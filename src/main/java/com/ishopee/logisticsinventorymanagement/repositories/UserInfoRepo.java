package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

    Boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserInfo SET mode =:mode WHERE id=:id")
    void updateUserStatus(Integer id, UserMode mode);

    @Modifying
    @Query("UPDATE UserInfo SET password=:pwd WHERE email=:username")
    void updatepassword(String username, String pwd);

}
