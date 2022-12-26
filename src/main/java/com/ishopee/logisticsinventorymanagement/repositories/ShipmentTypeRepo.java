package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentTypeRepo extends JpaRepository<ShipmentType, Integer> {

    //Register check
    @Query("SELECT count(ST.shipCode) from ShipmentType ST where ST.shipCode=:shipmentCode")
    Integer getShipmentCode(String shipmentCode);

    //Update Check
    @Query("SELECT count(ST.shipCode) from ShipmentType ST where ST.shipCode=:shipmentCode and ST.id !=:id")
    Integer getShipmentCodeCountForEdit(String shipmentCode, Integer id);

    // For Chart
    @Query("SELECT ST.shipMode,count(ST.shipMode) from ShipmentType ST group by ST.shipMode")
    List<Object[]> getShipModeAndCount();
}
