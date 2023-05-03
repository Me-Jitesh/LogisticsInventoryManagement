package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Integer> {

    @Query("SELECT pdtl FROM PurchaseDetails pdtl join pdtl.po as PurchaseOrder WHERE PurchaseOrder.id=:poId")
    List<PurchaseDetails> getPurchaseDetailsByPoId(Integer poId);

    @Query("SELECT count(pdtl.id) FROM PurchaseDetails pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id=:poId")
    Integer getPurchaseDetailsCountByPoId(Integer poId);

    @Query("SELECT pdtl FROM PurchaseDetails pdtl JOIN pdtl.part as part JOIN pdtl.po as po where part.id=:partId AND po.id=:poId")
    Optional<PurchaseDetails> getPurchaseDetailsByPartIdAndRepo(Integer partId, Integer poId);

    @Modifying
    @Query("UPDATE PurchaseDetails SET qty=qty+:newQty WHERE id=:pdtlId")
    Integer updateQtyByPdtlId(Integer pdtlId, Integer newQty);
}