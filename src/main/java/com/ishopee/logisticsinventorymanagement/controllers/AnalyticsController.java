package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.ishopee.logisticsinventorymanagement.services.IVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AnalyticsController {

    @Autowired
    private IVisitorService visitorService;

    @GetMapping("/")
    public String showAnalytics(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("visitorDetails") == null) {
            Visitor visitor = visitorService.saveVisitorDetails(httpServletRequest);
            System.err.println(visitor.getId() + " -> " + visitor.getIpAddress());
            httpServletRequest.getSession().setAttribute("visitorDetails", visitor);
        }
        return "Analytics";
    }
}
