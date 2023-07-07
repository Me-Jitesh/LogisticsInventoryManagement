package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;
import com.ishopee.logisticsinventorymanagement.models.Role;
import com.ishopee.logisticsinventorymanagement.repositories.RoleRepo;
import com.ishopee.logisticsinventorymanagement.services.IRoleService;
import com.ishopee.logisticsinventorymanagement.utilities.MyAppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepo repo;

    @Override
    public Boolean existsByRole(RoleType role) {
        return repo.existsByRole(role);
    }

    @Override
    public Integer saveRole(Role role) {
        return repo.save(role).getId();
    }

    @Override
    public List<Role> getAllRoles() {
        return repo.findAll();
    }

    @Override
    public Map<Integer, RoleType> getRolesMap() {
        List<Object[]> roleList = repo.getRoleIdAndRoleName();
        return MyAppUtility.convertListIntoMapRoleEnum(roleList);
    }

}
