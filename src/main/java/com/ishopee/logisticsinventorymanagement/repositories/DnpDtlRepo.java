package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.DnpDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DnpDtlRepo extends JpaRepository<DnpDtl, Integer> {
    @Query("UPDATE DnpDtl SET status=:status WHERE id=:id ")
    @Modifying
    void updateDnpDtlStatus(Integer id, String status);
}
