package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import com.ishopee.logisticsinventorymanagement.services.IOrderMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/om")
public class OrderMethodController {
    private static final Logger LOG = LoggerFactory.getLogger(MusController.class);

    @Autowired
    private IOrderMethodService service;

    @GetMapping("/register")
    public String orderMethodRegister() {
        return "OrderMethodRegister";
    }

    @PostMapping("/save")
    public String saveOrderMethod(@ModelAttribute OrderMethod orderMethod, Model model) {
        LOG.info("ENTERED INTO saveOrderMethod");
        try {
            System.out.println(orderMethod);
            Integer id = service.saveOrderMethod(orderMethod);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "order method " + id + " registered successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE OrderMethodRegister ! ");
        return "OrderMethodRegister";
    }

    @GetMapping("/all")
    public String getAllOrderMethod(Model model) {
        LOG.info("ENTERED INTO getAllOrderMethod");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllOrderMethod REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE OrderMethodData ! ");
        return "OrderMethodData";
    }

    private void fetchAllData(Model model) {
        List<OrderMethod> list = service.getAllOrderMethod();
        model.addAttribute("list", list);
    }
}
