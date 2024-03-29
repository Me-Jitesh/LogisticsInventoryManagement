package com.ishopee.logisticsinventorymanagement.services;


import com.ishopee.logisticsinventorymanagement.models.ProductUserType;

import java.util.List;
import java.util.Map;

public interface IProductUserTypeService {

    Integer saveProductUserType(ProductUserType productUserType);

    ProductUserType getProductUserTypeById(Integer id);

    List<ProductUserType> getAllProductUserType();

    void updateProductUserType(ProductUserType productUserType);

    void deleteProductUserType(Integer id);

    boolean isProductUserCodeExist(String productUserCode);

    boolean isProductUserCodeCountExistForEdit(String productUserCode, Integer id);

    List<Object[]> getProductUserTypeAndCount();

    boolean isEmailExist(String email);

    boolean isEmailExist(String email, Integer id);

    Map<Integer, String> getProductUserIdAndCode(String uType);
}
