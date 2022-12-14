package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import com.ishopee.logisticsinventorymanagement.utilities.ShipmentTypeUtility;
import com.ishopee.logisticsinventorymanagement.views.*;
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

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);
    @Autowired
    private ShipmentTypePdfUI pdfView;
    @Autowired
    private IShipmentTypeService service;
    @Autowired
    private ShipmentTypeCSVView csvView;
    @Autowired
    private ShipmentTypeXmlView xmlView;
    @Autowired
    private ShipmentTypeTextView textView;
    @Autowired
    private ServletContext context;
    @Autowired
    private ShipmentTypeUtility shipmentTypeUtil;


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
    public @ResponseBody String validateShipmentCode(@RequestParam String code, @RequestParam Integer id) {
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
        LOG.info("ENTERED INTO exportPdf METHOD");
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
        LOG.info("ENTERED INTO exportPdf METHOD");
        ByteArrayInputStream inputStream = pdfView.buildPdfDocument(service.getAllShipmentType());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;filename=ShipmentTypeData.pdf");
        LOG.debug("PDF EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/pdfone")
    public ResponseEntity<InputStreamResource> exportPdfById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportPdfById METHOD");
        ByteArrayInputStream inputStream = pdfView.buildPdfDocument(Arrays.asList(service.getShipmentType(id)));
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Disposition", "inline;filename=ShipmentTypeData.pdf");       // Only display on a browser not download automatically
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeData.pdf");     // download automatically
        LOG.debug("PDF EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> exportCSV() {
        LOG.info("ENTERED INTO exportCSV METHOD");
        ByteArrayInputStream inputStream = csvView.buildCSVDocument(service.getAllShipmentType());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataCSV.csv");
        LOG.debug("CSV EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/csvone")
    public ResponseEntity<InputStreamResource> exportCSVById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportCSVById METHOD");
        ByteArrayInputStream inputStream = csvView.buildCSVDocument(Arrays.asList(service.getShipmentType(id)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataCSV.csv");     // download automatically
        LOG.debug("CSV EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/json")
    public ResponseEntity<List<ShipmentType>> exportJSON() {
        LOG.info("ENTERED INTO exportJSON METHOD");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataJSON.json");
        LOG.debug("JSON EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).body(service.getAllShipmentType());
    }

    @GetMapping("/jsonone")
    public ResponseEntity<ShipmentType> exportJSONById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportJSONById METHOD");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataJSON.json");     // download automatically
        LOG.debug("JSON EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).body(service.getShipmentType(id));
    }

    @GetMapping("/text")
    public ResponseEntity<InputStreamResource> exportText() {
        LOG.info("ENTERED INTO exportText METHOD");
        ByteArrayInputStream byteArrayInputStream = textView.buildTextDocument(service.getAllShipmentType());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataText.txt");
        LOG.debug("TEXT EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.TEXT_PLAIN).body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/textone")
    public ResponseEntity<InputStreamResource> exportTextById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportTextById METHOD");
        ByteArrayInputStream byteArrayInputStream = textView.buildTextDocument(Arrays.asList(service.getShipmentType(id)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataText.txt");
        LOG.debug("TEXT EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.TEXT_PLAIN).body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/xml")
    public ResponseEntity<InputStreamResource> exportXml() {
        LOG.info("ENTERED INTO exportXml METHOD");
        ByteArrayInputStream inputStream = xmlView.buildXmlDocument(service.getAllShipmentType(), ShipmentType.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataXml.xml");
        LOG.debug("XML EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_XML).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/xmlone")
    public ResponseEntity<InputStreamResource> exportXmlById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportXmlById METHOD");
        ByteArrayInputStream inputStream = xmlView.buildXmlDocument(Arrays.asList(service.getShipmentType(id)), ShipmentType.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=ShipmentTypeDataXml.xml");     // download automatically
        LOG.debug("XML EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_XML).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/chart")
    public String getChart() {
        LOG.info("ENTERED INTO getChart METHOD");
        String path = context.getRealPath("/charts");
        shipmentTypeUtil.generatePieChart(path, service.getShipModeAndCount());
        shipmentTypeUtil.generateBarChart(path, service.getShipModeAndCount());
        LOG.info("CHART EXPORTATION SUCCEED !");
        return "ShipmentTypeChart";
    }

    private void fetchAllData(Model model) {
        List<ShipmentType> list = service.getAllShipmentType();
        model.addAttribute("list", list);
    }
}
