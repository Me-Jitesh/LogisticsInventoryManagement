package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.repositories.UserInfoRepo;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoRepo repo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Integer saveUserInfo(UserInfo userInfo) {
        String pwd = userInfo.getPassword();
        String encryptedPw = encoder.encode(pwd);
        userInfo.setPassword(encryptedPw);
        return repo.save(userInfo).getId();
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return repo.findAll();
    }

}
