package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

    Boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserInfo SET mode =:mode WHERE id=:id")
    void updateUserStatus(Integer id, UserMode mode);

}
