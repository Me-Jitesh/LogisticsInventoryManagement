package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentTypeRepo extends JpaRepository<ShipmentType, Integer> {
}
