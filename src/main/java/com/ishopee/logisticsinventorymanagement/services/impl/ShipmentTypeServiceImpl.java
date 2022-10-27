package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.repositories.ShipmentTypeRepo;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

    @Autowired
    private ShipmentTypeRepo shipmentTypeRepo;

    @Override
    public Integer saveShipmentType(ShipmentType st) {
        st = shipmentTypeRepo.save(st);
        return st.getId();
    }

    @Override
    public List<ShipmentType> getAllShipmentType() {
        return shipmentTypeRepo.findAll();
    }

    @Override
    public void deleteshipmentType(Integer id) {
        shipmentTypeRepo.deleteById(id);
    }
}
