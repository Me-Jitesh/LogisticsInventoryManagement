package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderMethodRepo extends JpaRepository<OrderMethod, Integer> {

    @Query("SELECT count(orderCode) FROM OrderMethod  WHERE orderCode=:orderCode")
    Integer isOrderCodeExist(String orderCode);

    @Query("SELECT count(orderCode) FROM OrderMethod  WHERE orderCode=:orderCode AND id=:id")
    Integer isOrderCodeExistForEdit(String orderCode, Integer id);

}
