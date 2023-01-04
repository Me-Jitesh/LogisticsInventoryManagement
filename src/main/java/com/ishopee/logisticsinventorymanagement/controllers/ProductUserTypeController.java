package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ProductUserType;
import com.ishopee.logisticsinventorymanagement.services.IProductUserTypeService;
import com.ishopee.logisticsinventorymanagement.utilities.ProductUserTypeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
@RequestMapping("/pu")
public class ProductUserTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductUserTypeController.class);

    @Autowired
    private IProductUserTypeService service;
    @Autowired
    private ServletContext context;
    @Autowired
    private ProductUserTypeUtility puUtil;


    @GetMapping("/register")
    public String productUserTypeRegister() {
        return "ProductUserTypeRegister";
    }

    @PostMapping("/save")
    public String saveProductUserType(@ModelAttribute ProductUserType productUserType, Model model) {
        LOG.info("ENTERED INTO saveProductUserType");
        try {
            Integer id = service.saveProductUserType(productUserType);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);
            String msg = "product user type " + id + " registered successfully";
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS SAVE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE ProductUserTypeRegister");
        return "ProductUserTypeRegister";
    }

    @GetMapping("/all")
    public String getProductUserType(Model model) {
        LOG.info("ENTERED INTO getProductUserType");
        try {
            fetchAllData(model);
            LOG.debug("FETCHED ALL RECORDS");
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS getAllProductUserType REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("ABOUT TO GO UI PAGE ProductUserTypeData");
        return "ProductUserTypeData";
    }

    @GetMapping("/delete")
    public String deleteProductUserType(@RequestParam Integer id, Model model) {
        LOG.info("ENTERED INTO DELETE deleteProductUserType METHOD");
        try {
            service.deleteProductUserType(id);
            String msg = "Product User Type " + id + " Deleted";
            LOG.debug(msg);
            fetchAllData(model);
            model.addAttribute("message", msg);
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS DELETE  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
        }
        LOG.info("ABOUT TO GO UI PAGE ProductUserTypeData");
        return "ProductUserTypeData";
    }

    @GetMapping("/edit")
    public String showProductUserTypeEdit(@RequestParam Integer id, Model model) {
        String page;
        LOG.info("ENTERED INTO EDIT METHOD");
        try {
            ProductUserType pu = service.getProductUserTypeById(id);
            LOG.debug("RECORD FOUND WITH ID {}", id);
            model.addAttribute("productUserType", pu);
            page = "ProductUserTypeEdit";
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS EDIT REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            fetchAllData(model);
            model.addAttribute("message", e.getMessage());
            page = "ProductUserTypeData";
        }
        LOG.info("ABOUT TO GO UI PAGE {} !", page);
        return page;
    }

    @PostMapping("/update")
    public String showProductUserTypeUpdate(@ModelAttribute ProductUserType pu) {
        LOG.info("ENTERED INTO showProductUserTypeUpdate METHOD");
        try {
            service.updateProductUserType(pu);
            LOG.debug("RECORD IS UPDATED FOR ID {}", pu.getId());
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS UPDATE REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
        }
        LOG.info("REDIRECTING TO FETCH ALL RECORD");
        return "redirect:all";
    }

    @GetMapping("/validatecode")
    @ResponseBody
    public String validateProductUserCode(@RequestParam String code, @RequestParam Integer id) {
        String msg = "";
//         for register check
        if (id == 0 && service.isProductUserCodeExist(code)) {
            msg = " *  " + code + " already exist";
//             for edit check
        } else if (id != 0 && service.isProductUserCodeCountExistForEdit(code, id)) {
            msg = " *  " + code + " already exist";
        }
        return msg;
    }

    @GetMapping("/chart")
    public String getChart() {
        LOG.info("ENTERED INTO getChart METHOD");
        String path = context.getRealPath("/charts");
        puUtil.generatePieChart(path, service.getProductUserTypeAndCount());
        puUtil.generateBarChart(path, service.getProductUserTypeAndCount());
        LOG.info("CHART EXPORTATION SUCCEED !");
        return "ProductUserTypeChart";
    }

    private void fetchAllData(Model model) {
        List<ProductUserType> list = service.getAllProductUserType();
        model.addAttribute("list", list);
    }
}
