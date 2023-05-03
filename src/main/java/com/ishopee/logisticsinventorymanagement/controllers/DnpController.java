package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.constants.PurchaseOrderStatus;
import com.ishopee.logisticsinventorymanagement.models.Dnp;
import com.ishopee.logisticsinventorymanagement.models.DnpDtl;
import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import com.ishopee.logisticsinventorymanagement.services.IDnpService;
import com.ishopee.logisticsinventorymanagement.services.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/dnp")
public class DnpController {

    @Autowired
    private IDnpService service;
    @Autowired
    private IPurchaseOrderService poService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        fetchPoCode(model);
        return "DnpRegister";
    }

    @PostMapping("/save")
    public String saveRegister(@ModelAttribute Dnp dnp, Model model) {
        try {
            createDnpDetailByPO(dnp);
            Integer id = service.saveDnp(dnp);
            if (id != null) {
                poService.updatePoStatus(dnp.getPo().getId(), PurchaseOrderStatus.RECEIVED.name());
                model.addAttribute("message", "REGISTRATION SUCCESS WITH ID : " + id);
            }
        } catch (Exception e) {
            model.addAttribute("message", "oooops ! something went wrong");
            e.printStackTrace();
        }

        fetchPoCode(model);
        return "DnpRegister";
    }

    @GetMapping("/all")
    public String showAllDnp(Model model) {
        model.addAttribute("dnps", service.getAllDnp());
        return "DnpData";
    }

    @GetMapping("/parts")
    public String showDnpDtl(@RequestParam Integer dnpId, Model model) {
        Dnp dnp = service.getOneDnp(dnpId);
        model.addAttribute("dnp", dnp);
        model.addAttribute("dnpDtls", dnp.getDnpDtl());
        return "DnpParts";
    }

    private void createDnpDetailByPO(Dnp dnp) {
        Integer id = dnp.getPo().getId();
        List<PurchaseDetails> pdtls = poService.getPurchaseDetailsByPoId(id);

        Set<DnpDtl> dtlSet = new HashSet<>();

        for (PurchaseDetails pdtl : pdtls) {
            DnpDtl dnpDtl = new DnpDtl();
            dnpDtl.setBaseCost(pdtl.getPart().getPartCost());
            dnpDtl.setQty(pdtl.getQty());
            dnpDtl.setPartCode(pdtl.getPart().getPartCode());
            dtlSet.add(dnpDtl);
        }
        dnp.setDnpDtl(dtlSet);
    }

    private void fetchPoCode(Model model) {
        Map<Integer, String> pos = poService.getPoIdAndCodeByStatus(PurchaseOrderStatus.INVOICED.name());
        model.addAttribute("poCodes", pos);
    }
}

