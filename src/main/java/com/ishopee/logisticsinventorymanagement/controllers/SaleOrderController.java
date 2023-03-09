package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.SaleOrder;
import com.ishopee.logisticsinventorymanagement.services.IProductUserTypeService;
import com.ishopee.logisticsinventorymanagement.services.ISaleOrderService;
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
@RequestMapping("/so")
public class SaleOrderController {

    private static final Logger LOG = LoggerFactory.getLogger(SaleOrderController.class);

    @Autowired
    private ISaleOrderService service;
    @Autowired
    private IShipmentTypeService shipmentService;
    @Autowired
    private IProductUserTypeService userTypeService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        LOG.debug("ENTERED INTO SHOW SALE ORDER REGISTER PAGE");
        fetchShipTypeCode("Yes", model);
        fetchCustomerCode("Customer", model);
        LOG.debug("EXITED FROM SHOW SALE ORDER REGISTER PAGE");
        return "SaleOrderRegister";
    }

    @PostMapping("/save")
    public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
        LOG.info("ENTERED INTO SAVE METHOD");
        try {
            Integer id = service.saveSaleOrder(saleOrder);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "Sale Order " + id + " Registered Successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            model.addAttribute("message", "Oooops Something Went Wrong......");
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE SaleOrderRegister ! ");
        return "SaleOrderRegister";
    }

    @GetMapping("/all")
    public String getAllSaleOrder(Model model) {
        LOG.info("ENTERED INTO getAllSaleOrder");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllSaleOrder {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE SaleOrderData ! ");
        return "SaleOrderData";
    }

    @GetMapping("/validatecode")
    @ResponseBody
    public String validateSoCode(@RequestParam String code, @RequestParam Integer id) {
        String msg = "";
//     for register
        if (id == 0 && service.isSaleCodeExist(code)) {
            msg = " * code " + code + " already exist";

//     for edit
        } else if (id != 0 && service.isSaleCodeExistForEdit(code, id)) {
            msg = " * " + code + " already exist";
        }
        return msg;
    }

    @GetMapping("/parts")
    public String showParts(@RequestParam Integer id, Model model) {
        LOG.debug("ENTERED INTO SHOW SALE ORDER PARTS PAGE");
        fetchSaleOrder(id, model);
        LOG.debug("EXITED FROM SHOW SALE ORDER PARTS PAGE");
        return "SaleOrderParts";
    }

    private void fetchAllData(Model model) {
        List<SaleOrder> list = service.getAllSaleOrder();
        model.addAttribute("list", list);
    }

    private void fetchShipTypeCode(String enable, Model model) {
        Map<Integer, String> shipData = shipmentService.getEnabledShipIdAndCode(enable);
        model.addAttribute("shipCodes", shipData);
    }

    private void fetchCustomerCode(String uType, Model model) {
        Map<Integer, String> customerData = userTypeService.getProductUserIdAndCode(uType);
        model.addAttribute("customers", customerData);
    }

    private void fetchSaleOrder(Integer id, Model model) {
        SaleOrder so = service.getSaleOrderById(id);
        model.addAttribute("so", so);
    }
}
