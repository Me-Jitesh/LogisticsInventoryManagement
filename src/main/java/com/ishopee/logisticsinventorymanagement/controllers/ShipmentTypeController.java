package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

    @Autowired
    private IShipmentTypeService service;

    @GetMapping("/register")
    public String showRegister() {
        return "ShipmentTypeRegister";
    }

    @PostMapping("/save")
    public String saveShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {
        Integer id = service.saveShipmentType(shipmentType);
        String msg = "ShipmentType " + id + " registered successfully";
        model.addAttribute("message", msg);
        return "ShipmentTypeRegister";
    }
}
