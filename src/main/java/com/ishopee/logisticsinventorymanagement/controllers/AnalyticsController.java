package com.ishopee.logisticsinventorymanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalyticsController {

    @GetMapping("/")
    public String showAnalytics() {
        return "Analytics";
    }
}
