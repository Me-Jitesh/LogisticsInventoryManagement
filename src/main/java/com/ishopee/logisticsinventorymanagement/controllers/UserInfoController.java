package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import com.ishopee.logisticsinventorymanagement.utilities.EmailUtil;
import com.ishopee.logisticsinventorymanagement.utilities.MyAppUtility;
import com.ishopee.logisticsinventorymanagement.utilities.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService service;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private EmailUtil emailUtil;

    @GetMapping("/setup")
    public String doSetup(HttpSession session, Principal principal) {
        String email = principal.getName();
        UserInfo info = service.getOneUserInfoByEmail(email);
        session.setAttribute("currentUser", info);
        session.setAttribute("isAdmin", UserInfoUtil.getRolesAsString(info.getRoles()).contains("ADMIN"));
        return "redirect:/userinfo/register";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        setRoleMap(model);
        return "UserInfoRegister";
    }

    @PostMapping("/save")
    public String saveUserData(@ModelAttribute UserInfo userInfo, Model model) {
        // Generate Dynamic Password & Set
        String pass = MyAppUtility.getPassword();
        userInfo.setPassword(pass);
        Integer id = service.saveUserInfo(userInfo);
        if (id != 0) {
            String text = "Name :: " + userInfo.getName() + "\n" +
                    "Username :: " + userInfo.getEmail() + "\n" +
                    "Password :: " + pass + "\n" +
                    "Roles :: " + UserInfoUtil.getRolesAsString(userInfo.getRoles());
            emailUtil.send(userInfo.getEmail(), "Your Credentials", text);
            model.addAttribute("message", "User Registered and Credentials Mail Sent");
        } else {
            model.addAttribute("message", "User Registration Failed");
        }
        setRoleMap(model);
        return "UserInfoRegister";
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        model.addAttribute("users", service.getAllUserInfo());
        return "UserData";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "UserLogin";
    }

    private void setRoleMap(Model model) {
        model.addAttribute("roleMap", roleService.getRolesMap());
    }
}
