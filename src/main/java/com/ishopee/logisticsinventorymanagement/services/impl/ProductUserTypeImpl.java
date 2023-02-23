package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.OrderMethodNotFoundException;
import com.ishopee.logisticsinventorymanagement.exceptions.ProductUserTypeException;
import com.ishopee.logisticsinventorymanagement.models.ProductUserType;
import com.ishopee.logisticsinventorymanagement.repositories.ProductUserTypeRepo;
import com.ishopee.logisticsinventorymanagement.services.IProductUserTypeService;
import com.ishopee.logisticsinventorymanagement.utilities.MyAppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductUserTypeImpl implements IProductUserTypeService {

    @Autowired
    private ProductUserTypeRepo repo;

    @Override
    public Integer saveProductUserType(ProductUserType productUserType) {
        return repo.save(productUserType).getId();
    }

    @Override
    public ProductUserType getProductUserTypeById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ProductUserTypeException("ProductUserType " + id + " Not Exist !"));
    }

    @Override
    public List<ProductUserType> getAllProductUserType() {
        return repo.findAll();
    }

    @Override
    public void updateProductUserType(ProductUserType pu) {
        if (pu.getId() == null || !repo.existsById(pu.getId()))
            throw new OrderMethodNotFoundException("Order Method Does Not Exist !");
        repo.save(pu);
    }

    @Override
    public void deleteProductUserType(Integer id) {
        repo.delete(getProductUserTypeById(id));
    }

    @Override
    public boolean isProductUserCodeExist(String productUserCode) {
        return repo.isUserCodeExist(productUserCode) > 0;
    }

    @Override
    public boolean isProductUserCodeCountExistForEdit(String productUserCode, Integer id) {
        return repo.isUserCodeExistForEdit(productUserCode, id) > 0;
    }

    @Override
    public List<Object[]> getProductUserTypeAndCount() {
        return repo.getProductUserTypeAndCount();
    }

    @Override
    public boolean isEmailExist(String email) {
        return repo.isEmailExist(email) > 0;

    }

    @Override
    public boolean isEmailExist(String email, Integer id) {
        return repo.isEmailExistForEdit(email, id) > 0;
    }

    @Override
    public Map<Integer, String> getProductUserIdAndCode(String uType) {
        List<Object[]> vendor = repo.getProductUserIdAndCodeByType(uType);
        return MyAppUtility.convertListIntoMap(vendor);
    }
}
