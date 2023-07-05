package com.ishopee.logisticsinventorymanagement.runner;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.repositories.RoleRepo;
import com.ishopee.logisticsinventorymanagement.repositories.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Order(20)
public class UserMasterDataRunner implements CommandLineRunner {

    @Autowired
    private UserInfoRepo repo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (!repo.existsByEmail("jiteshs101@gmail.com")) {
            UserInfo usr = new UserInfo();
            usr.setName("Jitesh Singh");
            usr.setEmail("jiteshs101@gmail.com");
            usr.setPassword("Hola@hola");
            usr.setMode(UserMode.ENABLED);
            usr.setRoles(roleRepo.findAll().stream().collect(Collectors.toSet()));
            repo.save(usr);
        }
    }
}
