package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

    Boolean existsByEmail(String email);
}
