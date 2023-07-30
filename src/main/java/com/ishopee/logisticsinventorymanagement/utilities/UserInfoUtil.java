package com.ishopee.logisticsinventorymanagement.utilities;

import com.ishopee.logisticsinventorymanagement.models.Role;

import java.util.Set;
import java.util.stream.Collectors;

public interface UserInfoUtil {
    static Set<String> getRolesAsString(Set<Role> roles) {
        return roles.stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
    }
}
