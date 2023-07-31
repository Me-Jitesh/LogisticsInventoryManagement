package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.ishopee.logisticsinventorymanagement.services.IVisitorService;
import com.ishopee.logisticsinventorymanagement.utilities.VisitorUtility;
import com.ishopee.logisticsinventorymanagement.views.AnalyticsPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/analytics")
public class AnalyticsController {

    private String IP_ADDRESS = null;
    @Autowired
    private IVisitorService visitorService;
    @Autowired
    private ServletContext context;
    @Autowired
    private VisitorUtility visitorUtility;

    @GetMapping("/")
    public String showAnalytics(HttpServletRequest httpServletRequest, Model model, @Value("${path.images}") String directory) {
        // Visiting First Time
        if (httpServletRequest.getSession().getAttribute("visitorDetails") == null) {
            Visitor visitor = visitorService.saveVisitorDetails(httpServletRequest);
            httpServletRequest.getSession().setAttribute("visitorDetails", visitor);
            IP_ADDRESS = visitor.getIpAddress();
        }
        // Generate Chart
        getChart(directory);
        // Display All Visitors
        model.addAttribute("list", visitorService.getRecent10Visitors());
        model.addAttribute("count", visitorService.getVisitorsCount());
        return "Analytics";
    }

    @GetMapping("/pdf")
    public ModelAndView exportPdf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new AnalyticsPdfView());
        modelAndView.addObject("visitors", visitorService.getRecent10Visitors());
        return modelAndView;
    }

    @GetMapping("/json")
    public ResponseEntity<List<Visitor>> exportJson(HttpServletRequest httpServletRequest) {
        System.err.println("JSON FILE EXPORTED BY " + httpServletRequest.getSession().getAttribute("visitorDetails"));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=Visitors.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(visitorService.getAllVisitors());
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Visitor> fetchAll() {
        return visitorService.getAllVisitors();
    }

    @GetMapping("/delete/{id}")
    public String deleteVisitor(@PathVariable Integer id, Model model) {
        try {
            visitorService.deleteVisitor(id);
            String msg = " Visitor  " + id + " Deleted";
            model.addAttribute("message", msg);
            prepareModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            prepareModel(model);
            model.addAttribute("message", e.getMessage());
        }
        return "Analytics";
    }

    @PostMapping("/saveAll")
    @ResponseBody
    public String saveVisitors(@RequestBody List<Visitor> visitors) {
        visitorService.saveAllVisitors(visitors);
        return "Success";
    }

    private void prepareModel(Model model) {
        model.addAttribute("list", visitorService.getRecent10Visitors());
        model.addAttribute("count", visitorService.getVisitorsCount());
    }

    public void getChart(String directory) {
//        Getting or Creating Path
        String path = context.getRealPath(directory);
        if (!Files.exists(Path.of(path))) {
            try {
                Files.createDirectory(Path.of(context.getRealPath(directory)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        Generating Charts
        visitorUtility.generatePieChart(path, visitorService.getVistorCountryCodeAndCount());
        visitorUtility.generateBarChart(path, visitorService.getVistorCountryCodeAndCount());
    }
}
