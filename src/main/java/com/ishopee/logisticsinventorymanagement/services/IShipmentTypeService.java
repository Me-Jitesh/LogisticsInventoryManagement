package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;

import java.util.List;

public interface IShipmentTypeService {

    Integer saveShipmentType(ShipmentType st);

    List<ShipmentType> getAllShipmentType();

    void deleteShipmentType(Integer id);

    ShipmentType getShipmentType(Integer id);

    void updateShipmentType(ShipmentType shipmentType);

    boolean isShipmentCodeExist(String shipmentCode);

    boolean isShipmentCodeCountExistForEdit(String shipmentCode, Integer id);

}
