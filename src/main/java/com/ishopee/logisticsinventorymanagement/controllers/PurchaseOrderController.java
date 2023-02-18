package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;
import com.ishopee.logisticsinventorymanagement.services.IPurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);

    @Autowired
    private IPurchaseOrderService service;

    @GetMapping("/register")
    public String showRegister() {
        LOG.info("Show Purchase Order Register Page");
        return "PurchaseOrderRegister";
    }

    @PostMapping("/save")
    public String savePurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
        LOG.info("ENTERED INTO SAVE METHOD");
        try {
            Integer id = service.savePurchaseOrder(purchaseOrder);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "Purchase Order " + id + " Registered Successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            model.addAttribute("Oooops Something Went Wrong......");
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE PurchaseOrderRegister ! ");
        return "PurchaseOrderRegister";
    }
}
