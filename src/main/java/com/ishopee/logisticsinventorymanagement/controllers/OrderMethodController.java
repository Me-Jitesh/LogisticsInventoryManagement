package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import com.ishopee.logisticsinventorymanagement.services.IOrderMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/delete")
    public String deleteOrderMethod(@RequestParam Integer id, Model model) {
        LOG.info("ENTERED INTO DELETE METHOD");
        try {
            service.deleteOrderMethod(id);
            String msg = "Order Method  " + id + " Deleted !";
            LOG.debug(msg);
            fetchAllData(model);
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS DELETE  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
        }
        LOG.info("ABOUT TO GO UI PAGE OrderMethodData ! ");
        return "OrderMethodData";
    }

    @GetMapping("/edit")
    public String ShowOrderMethodEdit(@RequestParam Integer id, Model model) {
        String page;
        LOG.info("ENTERED INTO EDIT METHOD");
        try {
            OrderMethod om = service.getOrderMethodById(id);
            LOG.debug("RECORD FOUND WITH ID {}", id);
            model.addAttribute("orderMethod", om);
            page = "OrderMethodEdit";
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS EDIT REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
            page = "OrderMethodData";
        }
        LOG.info("ABOUT TO GO UI PAGE {} !", page);
        return page;
    }

    @PostMapping("/update")
    public String updateOrderMethod(@ModelAttribute OrderMethod orderMethod) {
        LOG.info("ENTERED INTO UPDATE METHOD");
        try {
            service.updateOrderMethod(orderMethod);
            LOG.debug("RECORD IS UPDATED FOR ID {}", orderMethod.getId());
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS UPDATE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("REDIRECTING TO FETCH ALL RECORD ! ");
        return "redirect:all";
    }

    private void fetchAllData(Model model) {
        List<OrderMethod> list = service.getAllOrderMethod();
        model.addAttribute("list", list);
    }
}
