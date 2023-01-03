package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Part;
import com.ishopee.logisticsinventorymanagement.services.IPartService;
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
@RequestMapping("/part")
public class PartController {

    private static final Logger LOG = LoggerFactory.getLogger(PartController.class);

    @Autowired
    private IPartService service;


    @GetMapping("/register")
    public String showRegister() {
        return "PartRegister";
    }

    @PostMapping("/save")
    public String savePart(@ModelAttribute Part part, Model model) {
        LOG.info("ENTERED INTO savePart");
        try {
            Integer id = service.savePart(part);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "part " + id + " registered";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE PartRegister");
        return "PartRegister";
    }
}
