package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;

import java.util.List;

public interface IUserInfoService {

    Boolean existsByEmail(String email);

    Integer saveUserInfo(UserInfo userInfo);

    List<UserInfo> getAllUserInfo();

    UserInfo getOneUserInfoByEmail(String email);

    void updateUserStatus(Integer id, UserMode mode);

    void updateUserPassword(String username, String pwd);
}
