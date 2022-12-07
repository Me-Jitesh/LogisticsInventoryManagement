package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import com.ishopee.logisticsinventorymanagement.views.ShipmentTypeExcelView;
import com.ishopee.logisticsinventorymanagement.views.ShipmentTypePdfUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);
    @Autowired
    private IShipmentTypeService service;

    @Autowired
    ShipmentTypePdfUI pdfView;


    @GetMapping("/register")
    public String showRegister() {
        return "ShipmentTypeRegister";
    }

    @PostMapping("/save")
    public String saveShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {
        LOG.info("ENTERED INTO SAVE METHOD");
        try {
            Integer id = service.saveShipmentType(shipmentType);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "ShipmentType " + id + " registered successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE ShipmentTypeRegister ! ");
        return "ShipmentTypeRegister";
    }

    @GetMapping("/all")
    public String getAllShipmentType(Model model) {
        LOG.info("ENTERED INTO getAllShipmentType METHOD");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllShipmentType REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE ShipmentTypeData ! ");
        return "ShipmentTypeData";
    }

    @GetMapping("/delete")
    public String deleteShipmentType(@RequestParam Integer id, Model model) {
        LOG.info("ENTERED INTO DELETE METHOD");
        try {
            service.deleteShipmentType(id);
            String msg = "Shipment Type " + id + " Deleted !!";
            LOG.debug(msg);
            fetchAllData(model);
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS DELETE  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
        }
        LOG.info("ABOUT TO GO UI PAGE ShipmentTypeData ! ");
        return "ShipmentTypeData";
    }

    @GetMapping("/edit")
    public String ShowShipmentEdit(@RequestParam Integer id, Model model) {
        String page;
        LOG.info("ENTERED INTO EDIT METHOD");
        try {
            ShipmentType st = service.getShipmentType(id);
            LOG.debug("RECORD FOUND WITH ID {}", id);
            model.addAttribute("shipmentType", st);
            page = "ShipmentTypeEdit";
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS EDIT REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
            page = "ShipmentTypeData";
        }
        LOG.info("ABOUT TO GO UI PAGE {} !", page);
        return page;
    }

    @PostMapping("/update")
    public String updateShipmentType(@ModelAttribute ShipmentType shipmentType) {
        LOG.info("ENTERED INTO UPDATE METHOD");
        try {
            service.updateShipmentType(shipmentType);
            LOG.debug("RECORD IS UPDATED FOR ID {}", shipmentType.getId());
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS UPDATE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("REDIRECTING TO FETCH ALL RECORD ! ");
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

    @GetMapping("/excel")
    public ModelAndView exportExcel() {
        LOG.info("ENTERED INTO Export Excel METHOD");
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new ShipmentTypeExcelView());
            List<ShipmentType> list = service.getAllShipmentType();
            modelAndView.addObject("obs", list);
            LOG.debug("EXPORTATION SUCCEEDED !");
            return modelAndView;
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS Export Excel  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/excelone")
    public ModelAndView exportExcelById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportExcelById METHOD");
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new ShipmentTypeExcelView());
            modelAndView.addObject("obs", Arrays.asList(service.getShipmentType(id)));
            LOG.debug("EXPORTATION SINGLE EXCEL FILE SUCCEEDED !");
            return modelAndView;
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS exportExcelById REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {
        ByteArrayInputStream inputStream = pdfView.buildPdfDocument(service.getAllShipmentType());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;file=ShipmentTypeData.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/pdfone")
    public ResponseEntity<InputStreamResource> exportPdfById(@RequestParam Integer id) {
        ByteArrayInputStream inputStream = pdfView.buildPdfDocument(Arrays.asList(service.getShipmentType(id)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;file=ShipmentTypeData.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    private void fetchAllData(Model model) {
        List<ShipmentType> list = service.getAllShipmentType();
        model.addAttribute("list", list);
    }
}
