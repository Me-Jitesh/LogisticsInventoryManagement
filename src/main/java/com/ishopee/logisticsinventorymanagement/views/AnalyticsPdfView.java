package com.ishopee.logisticsinventorymanagement.views;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AnalyticsPdfView extends AbstractPdfView {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsPdfView.class);

    private PdfPTable table;
    private PdfPCell cell;
    private final Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 25, Color.WHITE);

    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) {
        LOG.info("ENTERED INTO buildPdfDocument ");
        List<Visitor> list = (List<Visitor>) model.get("visitors");
        response.addHeader("Content-Disposition", "inline;filename=Visitors.pdf");
        try {
            document.open();
            LOG.debug("TABLE UI CREATION");
            table = new PdfPTable(2);
            table.setWidthPercentage(100);
            setTitle("VISITOR DETAILS");
            setHeader();
            setData(list);
            document.add(table);
            LOG.debug("WRITTENED DATA INTO DOCUMENT");
        } catch (DocumentException de) {
            LOG.error(de.getMessage());
        }
        document.close();
        LOG.info("RETURNING TO CONTROLLER");
    }

    private void setTitle(String title) {
        //      Set  Title
        cell = new PdfPCell(new Paragraph(title, font));
        cell.setColspan(2);
        cell.setBorderColor(new Color(0, 0, 0));
        cell.setBackgroundColor(new Color(137, 245, 148));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(20);
        table.addCell(cell);
    }

    private void setHeader() {
//        Set Heading
        int padding = 10;
        Color bgColor = new Color(215, 216, 220);
        int textAlignment = Element.ALIGN_CENTER;

        cell = new PdfPCell(new Paragraph("IP ADDRESS"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("LOCATION DETAILS"));
        cell.setPadding(padding);
        cell.setHorizontalAlignment(textAlignment);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);
    }

    private void setData(List<Visitor> list) {
        // Set Data
        int padding = 5;
        int textAlignment = Element.ALIGN_CENTER;

        for (Visitor visitor : list) {
            cell = new PdfPCell(new Paragraph(visitor.getIpAddress()));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(localePara(visitor)));
            cell.setPadding(padding);
            cell.setHorizontalAlignment(textAlignment);
            table.addCell(cell);
        }
    }

    private String localePara(Visitor visitor) {
        String s = "country='" + visitor.getLocale().getCountry() + '\'' +
                ", countryCode='" + visitor.getLocale().getCountryCode() + '\'' +
                ", state='" + visitor.getLocale().getState() + '\'' +
                ", city='" + visitor.getLocale().getCity() + '\'' +
                ", zip='" + visitor.getLocale().getZip() + '\'' +
                ", latitude='" + visitor.getLocale().getLatitude() + '\'' +
                ", longitude='" + visitor.getLocale().getLongitude() + '\'' +
                ", timezone='" + visitor.getLocale().getTimezone() + '\'' +
                ", asn='" + visitor.getLocale().getAsn() + '\'' +
                ", as='" + visitor.getLocale().getAs() + '\'' +
                ", timestamp=" + visitor.getLocale().getTimestamp();
        return s;
    }
}
