package com.ishopee.logisticsinventorymanagement.utilities;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MyAppUtility {

    static Map<Integer, String> convertListIntoMap(List<Object[]> list) {
        return list.stream().collect(Collectors.toMap((ob) -> (Integer) ob[0], (ob) -> (String) ob[1]));
    }

    static Map<Integer, RoleType> convertListIntoMapRoleEnum(List<Object[]> list) {
        return list.stream().collect(Collectors.toMap((ob) -> (Integer) ob[0], (ob) -> (RoleType) ob[1]));
    }
}
