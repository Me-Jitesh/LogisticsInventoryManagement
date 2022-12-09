package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import com.ishopee.logisticsinventorymanagement.views.MusExcelView;
import com.ishopee.logisticsinventorymanagement.views.MusPdfUI;
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
@RequestMapping("mus")
public class MusController {

    private static final Logger LOG = LoggerFactory.getLogger(MusController.class);
    @Autowired
    private IMusService service;
    @Autowired
    private MusPdfUI musPdfUI;

    @GetMapping("/register")
    public String showMusRegister() {
        return "MusRegister";
    }

    @PostMapping("/save")
    public String saveMus(@ModelAttribute Mus mus, Model model) {
        try {
            Integer id = service.saveMus(mus);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "MUS " + id + " registered successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE MusRegister ! ");
        return "MusRegister";
    }

    @GetMapping("/all")
    public String getAllMus(Model model) {
        LOG.info("ENTERED INTO getAllMus METHOD");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllMus REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE MusData ! ");
        return "MusData";
    }

    @GetMapping("/delete")
    public String deleteMus(@RequestParam Integer id, Model model) {
        LOG.info("ENTERED INTO DELETE METHOD");
        try {
            service.deleteMus(id);
            String msg = " MUS  " + id + " Deleted !!";
            LOG.debug(msg);
            fetchAllData(model);
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS DELETE  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
        }
        LOG.info("ABOUT TO GO UI PAGE MusData ! ");
        return "MusData";
    }

    @GetMapping("/edit")
    public String editMus(@RequestParam Integer id, Model model) {
        String page;
        LOG.info("ENTERED INTO EDIT METHOD");
        try {
            Mus musItem = service.getMus(id);
            LOG.debug("RECORD FOUND WITH ID {}", id);
            model.addAttribute("musItem", musItem);
            page = "MusEdit";
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS EDIT REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
            page = "MusData";
        }
        LOG.info("ABOUT TO GO UI PAGE {} !", page);
        return page;
    }

    @PostMapping("/update")
    public String updateMus(@ModelAttribute Mus mus) {
        LOG.info("ENTERED INTO UPDATE METHOD");
        try {
            service.updateMus(mus);
            LOG.debug("RECORD IS UPDATED FOR ID {}", mus.getId());

        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS UPDATE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("REDIRECTING TO FETCH ALL RECORD ! ");
        return "redirect:all";
    }

    @GetMapping("/validatemodel")
    public @ResponseBody
    String validateMusModel(@RequestParam String musModel, @RequestParam Integer id) {
        String msg = "";
        // for register check
        if (id == 0 && service.isMusModelCountExist(musModel)) {
            msg = " * shipment code " + musModel + " already exist !";

            //for edit check
        } else if (id != 0 && service.isMusModelCountExistForEdit(musModel, id)) {
            msg = " *  " + musModel + " already exist !";
        }
        return msg;
    }

    @GetMapping("/excel")
    public ModelAndView exportExcel() {
        LOG.info("ENTERED INTO Export Excel METHOD");
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new MusExcelView());
            List<Mus> list = service.getAllMus();
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
            modelAndView.setView(new MusExcelView());
            modelAndView.addObject("obs", Arrays.asList(service.getMus(id)));
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
        ByteArrayInputStream inputStream = musPdfUI.buildPdfDocument(service.getAllMus());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;filename=MusData.pdf");
        LOG.debug("PDF EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    @GetMapping("/pdfone")
    public ResponseEntity<InputStreamResource> exportPdfById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportPdfById METHOD");
        ByteArrayInputStream inputStream = musPdfUI.buildPdfDocument(Arrays.asList(service.getMus(id)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=MusData.pdf");
        LOG.debug("PDF EXPORTATION SUCCEEDED !");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    private void fetchAllData(Model model) {
        List<Mus> list = service.getAllMus();
        model.addAttribute("list", list);
    }
}
