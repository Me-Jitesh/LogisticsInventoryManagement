package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

    @Autowired
    private IShipmentTypeService service;

}
