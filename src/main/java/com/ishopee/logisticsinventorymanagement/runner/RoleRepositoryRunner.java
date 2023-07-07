package com.ishopee.logisticsinventorymanagement.runner;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;
import com.ishopee.logisticsinventorymanagement.models.Role;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
public class RoleRepositoryRunner implements CommandLineRunner {

    @Autowired
    private IRoleService service;

    @Override
    public void run(String... args) {

        for (RoleType role : RoleType.values()) {
            if (!service.existsByRole(role)) {
                Role r = new Role();
                r.setRole(role);
                service.saveRole(r);
            }
        }

    }
}
