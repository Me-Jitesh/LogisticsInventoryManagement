package com.ishopee.logisticsinventorymanagement.runner;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Order(20)
public class UserMasterDataRunner implements CommandLineRunner {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (!userInfoService.existsByEmail("jiteshs101@gmail.com")) {
            UserInfo usr = new UserInfo();
            usr.setName("Jitesh Singh");
            usr.setEmail("jiteshs101@gmail.com");
            usr.setPassword("master@00");
            usr.setMode(UserMode.ENABLED);
            usr.setRoles(roleService.getAllRoles().stream().collect(Collectors.toSet()));
            usr.setOTP("000000");
            userInfoService.saveUserInfo(usr);
        }

        if (!userInfoService.existsByEmail("admin@ishopee.in")) {
            UserInfo usr = new UserInfo();
            usr.setName("Admin");
            usr.setEmail("admin@ishopee.in");
            usr.setPassword("admin@00");
            usr.setMode(UserMode.ENABLED);
            usr.setRoles(roleService.getAllRoles().stream().collect(Collectors.toSet()));
            usr.setOTP("000000");
            userInfoService.saveUserInfo(usr);
        }
    }
}
