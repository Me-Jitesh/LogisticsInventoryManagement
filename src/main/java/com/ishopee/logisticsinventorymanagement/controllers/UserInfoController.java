package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService service;
    @Autowired
    private IRoleService roleService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        setRoleMap(model);
        return "UserInfoRegister";
    }

    @PostMapping("/save")
    public String saveUserData(@ModelAttribute UserInfo userInfo, Model model) {
        Integer id = service.saveUserInfo(userInfo);
        model.addAttribute("message", "User Registered With ID " + id);
        setRoleMap(model);
        return "UserInfoRegister";
    }

    private void setRoleMap(Model model) {
        model.addAttribute("roleMap", roleService.getRolesMap());
    }
}
