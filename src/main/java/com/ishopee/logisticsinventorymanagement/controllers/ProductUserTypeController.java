package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.ProductUserType;
import com.ishopee.logisticsinventorymanagement.services.IProductUserTypeService;
import com.ishopee.logisticsinventorymanagement.utilities.EmailUtil;
import com.ishopee.logisticsinventorymanagement.utilities.ProductUserTypeUtility;
import com.ishopee.logisticsinventorymanagement.views.ProductUserTypeExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.Arrays;
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
    @Autowired
    private EmailUtil emailUtil;

    @GetMapping("/register")
    public String productUserTypeRegister(Model model) {
        setUserType(model);
        return "ProductUserTypeRegister";
    }

    @PostMapping("/save")
    public String saveProductUserType(@ModelAttribute ProductUserType productUserType, Model model) {
        LOG.info("ENTERED INTO saveProductUserType");
        try {
            // Sending Mail
            String subject = "Thanks For Registration";
            String text = "Hola! Dear \n\n You Are Registered With  : \n\n"
                    + "User Code :: " + productUserType.getUserCode() + "\n"
                    + "User Email :: " + productUserType.getUserEmail() + "\n"
                    + "User Contact :: " + productUserType.getUserContact() + "\n"
                    + "User  ID Number ::  " + productUserType.getUserIdNumber() + "\n"
                    + "\n\n\n\n\n\n\n\n\n"
                    + "* you need not to worry about  your data, it  is not stored only for testing purpose";
            boolean sent = emailUtil.send(productUserType.getUserEmail(), subject, text);

            // Storing In DB
            Integer id = service.saveProductUserType(productUserType);
            LOG.debug("RECORD IS CREATED WITH ID {}", id);

            // Prepare Message For Register Page Footer
            String msg;
            if (sent) {
                msg = "Product User Type " + id + " Registered Successfully and Email Sent";
                LOG.debug(msg);
            } else {
                msg = "Product User Type " + id + " Registered Successfully but Email Sending Failed ! ";
                LOG.debug(msg);
            }

            model.addAttribute("message", msg);
            setUserType(model);
        } catch (Exception e) {
            model.addAttribute("message", "Ooops! Something Went Wrong.....");
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

    @GetMapping("/validatemail")
    @ResponseBody
    public String validateEmail(@RequestParam String email, @RequestParam Integer id) {
        String msg = "";
//         for register check
        if (id == 0 && service.isEmailExist(email)) {
            msg = " *  " + email + " already exist";
//             for edit check
        } else if (id != 0 && service.isEmailExist(email, id)) {
            msg = " *  " + email + " already exist";
        }
        return msg;
    }

    @GetMapping("/excel")
    public ModelAndView exportExcel() {
        LOG.info("ENTERED INTO Export Excel METHOD");
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new ProductUserTypeExcelView());
            List<ProductUserType> list = service.getAllProductUserType();
            modelAndView.addObject("puList", list);
            LOG.debug("EXPORTATION ALL EXCEL FILE SUCCEEDED");
            return modelAndView;
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS EXPORT EXCEL  REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/excelone")
    public ModelAndView exportExcelById(@RequestParam Integer id) {
        LOG.info("ENTERED INTO exportExcelById METHOD");
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new ProductUserTypeExcelView());
            modelAndView.addObject("puList", Arrays.asList(service.getProductUserTypeById(id)));
            LOG.debug("EXPORTATION SINGLE EXCEL FILE SUCCEEDED");
            return modelAndView;
        } catch (Exception e) {
            LOG.error("UNABLE TO PROCESS exportExcelById REQUEST DUE TO {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
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

    private void setUserType(Model model) {
        model.addAttribute("productUserType", new ProductUserType());
    }
}
