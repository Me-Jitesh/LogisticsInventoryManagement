package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {

    @Query("SELECT count(orderCode) FROM PurchaseOrder  WHERE orderCode=:orderCode")
    Integer isOrderCodeExist(String orderCode);

    @Query("SELECT count(orderCode) FROM PurchaseOrder  WHERE orderCode=:orderCode AND id !=:id")
    Integer isOrderCodeExistForEdit(String orderCode, Integer id);

    @Query("SELECT status FROM PurchaseOrder WHERE id=:poId")
    String getCurrentPoStatus(Integer poId);

    @Modifying
    @Query("UPDATE PurchaseOrder SET status=:newStatus WHERE id =:poId")
    void updatePoStatus(Integer poId, String newStatus);
}
