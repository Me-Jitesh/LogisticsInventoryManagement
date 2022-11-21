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
        return "ShipmentTypeData";
    }

    @GetMapping("/delete")
    public String deleteShipmentType(@RequestParam Integer id, Model model) {
        service.deleteShipmentType(id);
        String msg = "Shipment Type " + id + " Deleted !!";
        List<ShipmentType> list = service.getAllShipmentType();
        model.addAttribute("list", list);
        model.addAttribute("message", msg);
        return "ShipmentTypeData";
    }

    @GetMapping("/edit")
    public String ShowShipmentEdit(@RequestParam Integer id, Model model) {
        ShipmentType st = service.getShipmentType(id);
        model.addAttribute("shipmentType", st);
        return "ShipmentTypeEdit";
    }

    @PostMapping("/update")
    public String updateShipmentType(@ModelAttribute ShipmentType shipmentType) {
        service.updateShipmentType(shipmentType);
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
