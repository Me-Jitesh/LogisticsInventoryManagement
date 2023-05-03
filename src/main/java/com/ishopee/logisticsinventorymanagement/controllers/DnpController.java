package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.constants.PurchaseOrderStatus;
import com.ishopee.logisticsinventorymanagement.models.Dnp;
import com.ishopee.logisticsinventorymanagement.services.IDnpService;
import com.ishopee.logisticsinventorymanagement.services.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dnp")
public class DnpController {

    @Autowired
    private IDnpService service;
    @Autowired
    private IPurchaseOrderService poService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        fetchPoCode(model);
        return "DnpRegister";
    }

    @PostMapping("/save")
    public String saveRegister(@ModelAttribute Dnp dnp, Model model) {
        Integer id = service.saveDnp(dnp);
        model.addAttribute("message", "Registration Success With ID : " + id);
        fetchPoCode(model);
        return "DnpRegister";
    }

    private void fetchPoCode(Model model) {
        Map<Integer, String> pos = poService.getPoIdAndCodeByStatus(PurchaseOrderStatus.INVOICED.name());
        model.addAttribute("poCodes", pos);
    }
}

