package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;

import java.util.List;

public interface IShipmentTypeService {

    Integer saveShipmentType(ShipmentType st);

    List<ShipmentType> getAllShipmentType();

    void deleteshipmentType(Integer id);

    ShipmentType getShipmentType(Integer id);

    void updateshipmentType(ShipmentType shipmentType);

}
