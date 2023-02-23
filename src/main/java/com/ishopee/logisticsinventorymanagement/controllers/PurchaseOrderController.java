package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;
import com.ishopee.logisticsinventorymanagement.services.IProductUserTypeService;
import com.ishopee.logisticsinventorymanagement.services.IPurchaseOrderService;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);

    @Autowired
    private IPurchaseOrderService service;
    @Autowired
    private IShipmentTypeService shipmentService;
    @Autowired
    private IProductUserTypeService userTypeService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        LOG.info("Entered Show Purchase Order Register Page");
        fetchShipTypeCode("Yes", model);
        fetchVendorCode("Vendor", model);
        LOG.info("Exit Show Purchase Order Register Page");
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

    @GetMapping("/all")
    public String getAllPurchaseOrder(Model model) {
        LOG.info("ENTERED INTO getAllPurchaseOrder");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllPurchaseOrder {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE PurchaseOrderData ! ");
        return "PurchaseOrderData";
    }

    @GetMapping("/validatecode")
    @ResponseBody
    public String validatePoCode(@RequestParam String code, @RequestParam Integer id) {
        String msg = "";
//     for register
        if (id == 0 && service.isOrderCodeExist(code)) {
            msg = " * code " + code + " already exist";

//     for edit
        } else if (id != 0 && service.isOrderCodeExistForEdit(code, id)) {
            msg = " * " + code + " already exist";
        }
        return msg;
    }

    private void fetchAllData(Model model) {
        List<PurchaseOrder> list = service.getAllPurchaseOrder();
        model.addAttribute("list", list);
    }

    private void fetchShipTypeCode(String enable, Model model) {
        Map<Integer, String> shipData = shipmentService.getEnabledShipIdAndCode(enable);
        model.addAttribute("shipCodes", shipData);
    }

    private void fetchVendorCode(String uType, Model model) {
        Map<Integer, String> vendorData = userTypeService.getProductUserIdAndCode(uType);
        model.addAttribute("vendors", vendorData);
    }
}
