package com.ishopee.logisticsinventorymanagement.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class HealthStatus {

    @GetMapping("/ping")
    public String pingMe() {
        return "{\"Status\":\"OK\"}";
    }
}
