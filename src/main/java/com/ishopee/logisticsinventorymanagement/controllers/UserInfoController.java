package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import com.ishopee.logisticsinventorymanagement.utilities.EmailUtil;
import com.ishopee.logisticsinventorymanagement.utilities.MyAppUtility;
import com.ishopee.logisticsinventorymanagement.utilities.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService service;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/setup")
    public String doSetup(HttpSession session, Principal principal) {
        String email = principal.getName();
        UserInfo info = service.getOneUserInfoByEmail(email);
        session.setAttribute("currentUser", info);
        session.setAttribute("isAdmin", UserInfoUtil.getRolesAsString(info.getRoles()).contains("ADMIN"));
        return "redirect:/st/all";
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
        String OTP = MyAppUtility.getOTP();
        userInfo.setOTP(OTP);
        Integer id = service.saveUserInfo(userInfo);
        if (id != 0) {
            String text = "Name :: " + userInfo.getName() + "\n" + "Username :: " + userInfo.getEmail() + "\n" + "Password :: " + pass + "\n" + "Roles :: " + UserInfoUtil.getRolesAsString(userInfo.getRoles()) + "\n" + "OTP :: " + OTP;
            System.err.println(text);
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

    @GetMapping("/enable")
    public String enableMode(@RequestParam Integer id) {
        service.updateUserStatus(id, UserMode.ENABLED);
        return "redirect:all";
    }

    @GetMapping("/disable")
    public String disableMode(@RequestParam Integer id) {
        service.updateUserStatus(id, UserMode.DISABLED);
        return "redirect:all";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        UserInfo info = (UserInfo) session.getAttribute("currentUser");
        Set<String> roles = UserInfoUtil.getRolesAsString(info.getRoles());
        model.addAttribute("userInfo", info);
        model.addAttribute("roles", roles);
        return "UserProfile";
    }

    @GetMapping("/resetPwd")
    public String showForgotPwdPage() {
        return "ForgetPasswordPage";
    }

    @PostMapping("/updatePwd")
    public String reGenNewPwd(@RequestParam String username, Model model) {
        if (service.existsByEmail(username)) {
            UserInfo userInfo = service.getOneUserInfoByEmail(username);
            String pwd = MyAppUtility.getPassword();
            String encdPwd = encoder.encode(pwd);
            service.updateUserPassword(username, encdPwd);

            String text = "Name :: " + userInfo.getName() + "\n" + "Username :: " + userInfo.getEmail() + "\n" + "New Password :: " + pwd + "\n" + "Roles :: " + UserInfoUtil.getRolesAsString(userInfo.getRoles());
            System.err.println(text);
            emailUtil.send(userInfo.getEmail(), "Your Credentials Updated", text);
            model.addAttribute("message", "PASSWORD UPADTED CHECK YOUR INBOX FOR CREDENTIALS");

        } else {
            model.addAttribute("message", "USER DOES NOT EXIST ! PLEASE REGISTER");
        }
        return "ForgetPasswordPage";
    }

    @GetMapping("/customPwd")
    public String showCustomPassword() {
        return "CustomPassword";
    }

    @PostMapping("/saveCustomPwd")
    public String saveCustomPassword(@RequestParam String password, HttpSession session) {
        UserInfo info = (UserInfo) session.getAttribute("currentUser");
        String encPwd = encoder.encode(password);
        service.updateUserPassword(info.getEmail(), encPwd);
        System.err.println(info.getEmail() + " Password Changed ! as " + password);
        return "redirect:profile";
    }

    @GetMapping("/activation")
    public String showActivation() {
        return "OtpActivation";
    }

    @PostMapping("/verification")
    public String OtpVerification(@RequestParam String username, @RequestParam String otp, Model model) {
        try {
            UserInfo info = service.getOneUserInfoByEmail(username);
            if (info.getOTP().equals(otp)) {
                service.updateUserStatus(info.getId(), UserMode.ENABLED);
                model.addAttribute("message", "User Activated Successfully ! Now Login !");
            } else {
                model.addAttribute("message", "Activation Failed ! INCORRECT OTP !");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Email Not Exist ! Please Register");
            e.printStackTrace();
        }
        return "OtpActivation";
    }

    private void setRoleMap(Model model) {
        model.addAttribute("roleMap", roleService.getRolesMap());
    }
}
