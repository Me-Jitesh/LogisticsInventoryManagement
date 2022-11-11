package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.ShipmentTypeNotFoundException;
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
//        shipmentTypeRepo.deleteById(id);
        shipmentTypeRepo.delete(getShipmentType(id)); // Exception Handling
    }

    /*      // Old Method of Exception Handling
        @Override
        public ShipmentType getShipmentType(Integer id) {
            Optional<ShipmentType> opt = shipmentTypeRepo.findById(id); // may be null

            if (opt.isPresent()) {
                return opt.get();
            } else {
                throw new ShipmentTypeNotFoundException("Shipment Type " + id + " Not Exist !");
            }
        }
    */

    // New Method for Exception Handling(java 8)
    @Override
    public ShipmentType getShipmentType(Integer id) {
        return shipmentTypeRepo.findById(id).orElseThrow(() -> new ShipmentTypeNotFoundException("Shipment Type " + id + " Not Exist !"));
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

    @Override
    public boolean isShipmentCodeCountExistForEdit(String shipmentCode, Integer id) {
        return shipmentTypeRepo.getShipmentCodeCountForEdit(shipmentCode, id) > 0;
    }
}
