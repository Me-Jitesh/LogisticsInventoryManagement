package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;
import com.ishopee.logisticsinventorymanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Boolean existsByRole(RoleType role);

    @Query("SELECT id,role FROM Role ")
    List<Object[]> getRoleIdAndRoleName();
}
