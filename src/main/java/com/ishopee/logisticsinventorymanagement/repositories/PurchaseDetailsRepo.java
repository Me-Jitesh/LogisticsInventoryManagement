package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Integer> {

    @Query("SELECT pdtl FROM PurchaseDetails pdtl join pdtl.po as PurchaseOrder WHERE PurchaseOrder.id=:poId")
    List<PurchaseDetails> getPurchaseDetailsByPoId(Integer poId);

    @Query("SELECT count(pdtl.id) FROM PurchaseDetails pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id=:poId")
    Integer getPurchaseDetailsCountByPoId(Integer poId);
}