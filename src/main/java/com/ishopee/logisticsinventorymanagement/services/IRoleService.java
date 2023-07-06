package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;

import java.util.Map;

public interface IRoleService {
    Map<Integer, RoleType> getRolesMap();
}
