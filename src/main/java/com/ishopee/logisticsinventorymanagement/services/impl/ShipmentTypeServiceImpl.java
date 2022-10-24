package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.repositories.ShipmentTypeRepo;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

    @Autowired
    private ShipmentTypeRepo shipmentTypeRepo;
}
