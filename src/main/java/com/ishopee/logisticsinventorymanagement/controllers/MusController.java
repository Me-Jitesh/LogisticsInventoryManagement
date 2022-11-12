package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
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

    @GetMapping("/register")
    public String showMusRegister() {
        return "MusRegister";
    }

    @PostMapping("/save")
    public String saveMus(@ModelAttribute Mus mus, Model model) {
        Integer id = service.saveMus(mus);
        String msg = "MUS " + id + " registered successfully";
        model.addAttribute("message", msg);
        return "MusRegister";
    }

    @GetMapping("/all")
    public String getAllMus(Model model) {
        List<Mus> list = service.getAllMus();
        model.addAttribute("list", list);
        return "MusData";
    }

    @GetMapping("/delete")
    public String deleteMus(@RequestParam Integer id, Model model) {
        service.deleteMus(id);
        String msg = " MUS  " + id + " Deleted !!";
        List<Mus> list = service.getAllMus();
        model.addAttribute("list", list);
        model.addAttribute("message", msg);
        return "MusData";
    }

    @GetMapping("/edit")
    public String editMus(@RequestParam Integer id, Model model) {
        String page;
        try {
            Mus musItem = service.getMus(id);
            model.addAttribute("musItem", musItem);
            page = "MusEdit";
        } catch (Exception e) {
            e.printStackTrace();
            List<Mus> list = service.getAllMus();
            model.addAttribute("list", list);
            model.addAttribute("message", e.getMessage());
            page = "MusData";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateMus(@ModelAttribute Mus mus) {
        service.updateMus(mus);
        return "redirect:all";
    }
}
