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
    public void deleteShipmentType(Integer id) {
//        shipmentTypeRepo.deleteById(id);
        shipmentTypeRepo.delete(getShipmentType(id));
    }

    /*
    //    Old Method For Exception Handling Before Java8
        @Override
        public ShipmentType getShipmentType(Integer id) {
            Optional<ShipmentType> opt = shipmentTypeRepo.findById(id); // may be null
            if (opt.isPresent()) {
                return opt.get();
            } else {
                throw new ShipmentTypeNotFoundException("Shipment Type " + id + " does not exist !");
            }
        }
    */

    //    New Method For Exception Handling Java8 onwards
    @Override
    public ShipmentType getShipmentType(Integer id) {
        return shipmentTypeRepo.findById(id).orElseThrow(() -> new ShipmentTypeNotFoundException("Shipment Type " + id + " does not exist !"));
    }

    @Override
    public void updateShipmentType(ShipmentType shipmentType) {
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

    @Override
    public List<Object[]> getShipModeAndCount() {
        return shipmentTypeRepo.getShipModeAndCount();
    }
}
