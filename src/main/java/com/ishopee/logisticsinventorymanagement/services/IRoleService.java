package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;
import com.ishopee.logisticsinventorymanagement.models.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    Boolean existsByRole(RoleType role);

    Integer saveRole(Role role);

    List<Role> getAllRoles();

    Map<Integer, RoleType> getRolesMap();
}
