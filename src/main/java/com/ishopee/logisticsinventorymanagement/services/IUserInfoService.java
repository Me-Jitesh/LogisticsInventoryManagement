package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.UserInfo;

import java.util.List;

public interface IUserInfoService {

    Integer saveUserInfo(UserInfo userInfo);

    List<UserInfo> getAllUserInfo();

}
