package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Integer> {
}