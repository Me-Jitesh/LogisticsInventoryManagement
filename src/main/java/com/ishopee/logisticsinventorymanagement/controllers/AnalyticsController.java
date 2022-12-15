package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.ishopee.logisticsinventorymanagement.services.IVisitorService;
import com.ishopee.logisticsinventorymanagement.views.AnalyticsPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AnalyticsController {

    @Autowired
    private IVisitorService visitorService;

    @GetMapping("/")
    public String showAnalytics(HttpServletRequest httpServletRequest, Model model) {
        // Visiting First Time
        if (httpServletRequest.getSession().getAttribute("visitorDetails") == null) {
            Visitor visitor = visitorService.saveVisitorDetails(httpServletRequest);
            httpServletRequest.getSession().setAttribute("visitorDetails", visitor);
        }
        // Display All Visitors
        model.addAttribute("list", visitorService.getRecent10Visitors());
        model.addAttribute("count", visitorService.getVisitorsCount());
        return "Analytics";
    }

    @GetMapping("/pdf")
    public ModelAndView exportPdf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new AnalyticsPdfView());
        modelAndView.addObject("visitors", visitorService.getAllVisitors());
        return modelAndView;
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
            pepareModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            pepareModel(model);
            model.addAttribute("message", e.getMessage());
        }
        return "Analytics";
    }

    private void pepareModel(Model model) {
        model.addAttribute("list", visitorService.getRecent10Visitors());
        model.addAttribute("count", visitorService.getVisitorsCount());
    }
}
