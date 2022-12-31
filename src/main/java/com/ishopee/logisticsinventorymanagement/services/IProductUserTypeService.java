package com.ishopee.logisticsinventorymanagement.services;


import com.ishopee.logisticsinventorymanagement.models.ProductUserType;

import java.util.List;

public interface IProductUserTypeService {

    Integer saveProductUserType(ProductUserType productUserType);

    ProductUserType getProductUserTypeById(Integer id);

    List<ProductUserType> getAllProductUserType();

    void updateProductUserType(ProductUserType productUserType);

    void deleteProductUserType(Integer id);

    boolean isProductUserCodeExist(String productUserCode);

    boolean isProductUserCodeCountExistForEdit(String productUserCode, Integer id);

    List<Object[]> getProductUserTypeAndCount();
}
