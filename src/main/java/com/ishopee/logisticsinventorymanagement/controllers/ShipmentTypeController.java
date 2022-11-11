package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public String getAllShipmentType(Model model) {
        List<ShipmentType> list = service.getAllShipmentType();
        model.addAttribute("list", list);
        System.out.println(list.toString());
        return "ShipmentTypeData";
    }

    @GetMapping("/delete")
    public String deletelShipmentType(@RequestParam Integer id, Model model) {
        try {
            // if data exist
            service.deleteshipmentType(id);
            List<ShipmentType> list = service.getAllShipmentType();
            String msg = "Shipment Type " + id + " Deleted !!";
            model.addAttribute("list", list);
            model.addAttribute("message", msg);
        } catch (Exception e) {
            // if data not exist
            e.printStackTrace();
            List<ShipmentType> list = service.getAllShipmentType();
            model.addAttribute("list", list);
            model.addAttribute("message", e.getMessage());
        }
        return "ShipmentTypeData";
    }

    @GetMapping("/edit")
    public String ShowshipmentEdit(@RequestParam Integer id, Model model) {
        String page;
        try {
            // If Data Exist
            ShipmentType st = service.getShipmentType(id);
            model.addAttribute("shipmentType", st);
            page = "ShipmentTypeEdit";
        } catch (Exception e) {
            // If Data Not Exist
            e.printStackTrace();
            List<ShipmentType> list = service.getAllShipmentType();
            model.addAttribute("list", list);
            model.addAttribute("message", e.getMessage());
            page = "ShipmentTypeData";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateShipmentType(@ModelAttribute ShipmentType shipmentType) {
        service.updateshipmentType(shipmentType);
        return "redirect:all";
    }

    @GetMapping("/validatecode")
    public @ResponseBody
    String validateShipmentCode(@RequestParam String code, @RequestParam Integer id) {
        String msg = "";
        // for register check
        if (id == 0 && service.isShipmentCodeExist(code)) {
            msg = " * shipment code " + code + " already exist !";

            //for edit check
        } else if (id != 0 && service.isShipmentCodeCountExistForEdit(code, id)) {
            msg = " *  " + code + " already exist !";
        }
        return msg;
    }
}
