package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("mus")
public class MusController {

    @Autowired
    private IMusService service;
    private static final Logger LOG = LoggerFactory.getLogger(MusController.class);

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

    private void fetchAllData(Model model) {
        List<Mus> list = service.getAllMus();
        model.addAttribute("list", list);
    }
}
