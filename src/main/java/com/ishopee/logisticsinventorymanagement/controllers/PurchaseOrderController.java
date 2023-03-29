package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;
import com.ishopee.logisticsinventorymanagement.services.IPartService;
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

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private IPurchaseOrderService service;
    @Autowired
    private IShipmentTypeService shipmentService;
    @Autowired
    private IProductUserTypeService userTypeService;
    @Autowired
    private IPartService partService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        LOG.debug("ENTERED INTO SHOW PURCHASE ORDER REGISTER PAGE");
        fetchShipTypeCode("Yes", model);
        fetchVendorCode("Vendor", model);
        LOG.debug("EXITED FROM SHOW PURCHASE ORDER REGISTER PAGE");
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

    @GetMapping("/parts")
    public String showPoParts(@RequestParam Integer id, Model model) {
        LOG.debug("ENTERED INTO SHOW PURCHASE ORDER PARTS");
        fetchPurchaseOrder(id, model);
        fetchPartCode(model);
        fetchPurchaseDetails(id, model);
        LOG.debug("EXITED FROM SHOW PURCHASE ORDER PARTS");
        return "PurchaseOrderParts";
    }

    @PostMapping("/addpart")
    public String addPart(@ModelAttribute PurchaseDetails pdtl) {
        LOG.debug("ENTERED INTO ADD PART METHOD");
        service.savePurchaseOrderDetails(pdtl);
        LOG.debug("EXITED FROM ADD PART METHOD");
        return "redirect:parts?id=" + pdtl.getPo().getId();
    }

    @GetMapping("/deletePart")
    public String deletePart(@RequestParam Integer pdtlId, @RequestParam Integer poId) {
        LOG.debug("ENTERED INTO DELETE PART METHOD");
        service.deletePurchaseDetail(pdtlId);
        LOG.debug("EXITED FROM DELETE PART METHOD");
        return "redirect:parts?id=" + poId;
    }

    private void fetchAllData(Model model) {
        List<PurchaseOrder> list = service.getAllPurchaseOrder();
        model.addAttribute("list", list);
    }

    private void fetchPurchaseOrder(Integer id, Model model) {
        PurchaseOrder po = service.getPurchaseOrderById(id);
        model.addAttribute("po", po);
    }

    private void fetchShipTypeCode(String enable, Model model) {
        Map<Integer, String> shipData = shipmentService.getEnabledShipIdAndCode(enable);
        model.addAttribute("shipCodes", shipData);
    }

    private void fetchVendorCode(String uType, Model model) {
        Map<Integer, String> vendorData = userTypeService.getProductUserIdAndCode(uType);
        model.addAttribute("vendors", vendorData);
    }

    private void fetchPartCode(Model model) {
        Map<Integer, String> part = partService.getPartIdAndCode();
        model.addAttribute("parts", part);
    }

    private void fetchPurchaseDetails(Integer id, Model model) {
        List<PurchaseDetails> pdtlList = service.getPurchaseDetailsByPoId(id);
        model.addAttribute("pdtlList", pdtlList);
    }
}
