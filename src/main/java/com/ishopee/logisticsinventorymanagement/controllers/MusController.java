package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mus")
public class MusController {

    @Autowired
    private IMusService service;

    @GetMapping("/register")
    public String showMusRegister() {
        return "MusRegister";
    }

    @PostMapping("/save")
    public String saveShipmentType(@ModelAttribute Mus mus, Model model) {
        Integer id = service.saveMus(mus);
        String msg = "MUS " + id + " registered successfully";
        model.addAttribute("message", msg);
        return "MusRegister";
    }
}
