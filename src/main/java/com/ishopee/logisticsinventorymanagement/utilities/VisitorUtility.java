package com.ishopee.logisticsinventorymanagement.utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.ishopee.logisticsinventorymanagement.models.VisitorLocation;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class VisitorUtility {
    @Autowired
    private APISecretKeys apiSecretKeys;

    public Visitor resolveVisitorDetails(HttpServletRequest httpServletRequest) {
        Visitor visitor = new Visitor();
        String ip = extractIP(httpServletRequest);
        visitor.setIpAddress(ip);
//        VisitorLocation locale = extractLocaleByGeoLite2(ip, httpServletRequest.getServletContext().getRealPath("/extra_resources/GeoLite2-City-DB/"));
        VisitorLocation locale = extractLocaleByIP2Location(ip);
        visitor.setLocale(locale);
        return visitor;
    }

    private String extractIP(HttpServletRequest request) {
        String LOCALHOST_IPV4 = "127.0.0.1";
        String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!StringUtils.isEmpty(ipAddress) && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    private VisitorLocation extractLocaleByGeoLite2(String ip, String path) {
        String dbLocation = path + "GeoLite2-City.mmdb";
        VisitorLocation visitorLocation = new VisitorLocation();
        visitorLocation.setTimestamp(Timestamp.from(Instant.now()));
        try {
            File databse = new File(dbLocation);
            DatabaseReader dbr = new DatabaseReader.Builder(databse).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CityResponse response = dbr.city(inetAddress);
            // Extracting Locale and Setting to the VisitorLocation Model
            visitorLocation.setCountry(response.getCountry().getName());
            visitorLocation.setCountryCode(response.getCountry().getIsoCode());
            visitorLocation.setState(response.getLeastSpecificSubdivision().getName());
            visitorLocation.setCity(response.getCity().getName());
            visitorLocation.setZip(response.getPostal().getCode());
            visitorLocation.setLongitude(response.getLocation().getLongitude().toString());
            visitorLocation.setLatitude(response.getLocation().getLatitude().toString());
            visitorLocation.setAsn(String.valueOf(response.getTraits().getAutonomousSystemNumber())); // Paid Plan
            visitorLocation.setAs(response.getTraits().getIsp()); // Paid Plan
            visitorLocation.setTimezone(response.getLocation().getTimeZone());
            System.err.println(response.getTraits()); // only to know about connection
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
        return visitorLocation;
    }

    public VisitorLocation extractLocaleByIP2Location(String ip) {
        String key = apiSecretKeys.getIP2LocationKey();
        VisitorLocation visitorLocation = new VisitorLocation();
        visitorLocation.setTimestamp(Timestamp.from(Instant.now()));
        try {
            // API Call for Geo Location
//            Scanner s = new Scanner(new URL("https://api.ip2location.io/?key=" + key + "&ip=" + ip).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
            String url = "https://api.ip2location.io/?key=" + key + "&ip=" + ip;
            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            var client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON Parsing Using Gson
//            JsonObject jsonObject = new JsonParser().parse(s.next()).getAsJsonObject();
            if (response.statusCode() == 200) {
                JsonObject jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();

                visitorLocation.setCountryCode(jsonObject.get("country_code").getAsString());
                visitorLocation.setCountry(jsonObject.get("country_name").getAsString());
                visitorLocation.setState(jsonObject.get("region_name").getAsString());
                visitorLocation.setCity(jsonObject.get("city_name").getAsString());
                visitorLocation.setLatitude(jsonObject.get("latitude").getAsString());
                visitorLocation.setLongitude(jsonObject.get("longitude").getAsString());
                visitorLocation.setZip(jsonObject.get("zip_code").getAsString());
                visitorLocation.setTimezone(jsonObject.get("time_zone").getAsString());
                visitorLocation.setAsn(jsonObject.get("asn").getAsString());
                visitorLocation.setAs(jsonObject.get("as").getAsString());
            } else {
                throw new Exception(String.valueOf(response.statusCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visitorLocation;
    }

    public void generatePieChart(String path, List<Object[]> data) {
        try {
            // Prepare Data Source
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Object[] ob : data)
                dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
            // Create JFreeChart Object
            JFreeChart chart = ChartFactory.createPieChart3D("PIE", dataset);
            chart.getTitle().setFont(new Font("Ubuntu", Font.PLAIN, 12));
            chart.setBorderPaint(Color.DARK_GRAY);
            chart.setBackgroundPaint(Color.lightGray);
            chart.getPlot().setBackgroundPaint(Color.LIGHT_GRAY);
            chart.getLegend().setBackgroundPaint(Color.lightGray);
            // Save As Image
            ChartUtils.saveChartAsPNG(new File(path + "/pie.png"), chart, 200, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateBarChart(String path, List<Object[]> data) {
        try {
            // Prepare Data Source
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] ob : data)
                dataset.setValue(Double.valueOf(ob[1].toString()), ob[0].toString(), "");
            // Create JFreeChart Object
            JFreeChart chart = ChartFactory.createBarChart("BAR ", "COUNTRY", "COUNT", dataset);
            chart.getTitle().setFont(new Font("Ubuntu", Font.PLAIN, 15));
            chart.setBorderPaint(Color.DARK_GRAY);
            chart.setBackgroundPaint(Color.lightGray);
            chart.getPlot().setBackgroundPaint(Color.LIGHT_GRAY);
            // Save As Image
            ChartUtils.saveChartAsPNG(new File(path + "/bar.png"), chart, 250, 250);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}