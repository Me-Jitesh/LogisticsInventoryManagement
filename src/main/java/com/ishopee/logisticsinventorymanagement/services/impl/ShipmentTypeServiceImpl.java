package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.ShipmentType;
import com.ishopee.logisticsinventorymanagement.repositories.ShipmentTypeRepo;
import com.ishopee.logisticsinventorymanagement.services.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ShipmentType getShipmentType(Integer id) {
        Optional<ShipmentType> opt = shipmentTypeRepo.findById(id); // may be null
        if (opt.isPresent()) {
            return opt.get();
        } else {
//            throw new Exception(ShipmentTypeNotFoundException);
        }
        return null;
    }

    @Override
    public void updateshipmentType(ShipmentType shipmentType) {
        shipmentTypeRepo.save(shipmentType);
    }

    @Override
    public boolean isShipmentCodeExist(String shipmentCode) {
        Integer count = shipmentTypeRepo.getShipmentCode(shipmentCode);
        boolean flag = count > 0 ? true : false;
        return flag;
    }
}
