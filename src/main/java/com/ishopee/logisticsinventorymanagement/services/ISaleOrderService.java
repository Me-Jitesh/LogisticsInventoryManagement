package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.SaleOrder;

import java.util.List;

public interface ISaleOrderService {

    Integer saveSaleOrder(SaleOrder so);

    SaleOrder getSaleOrderById(Integer id);

    List<SaleOrder> getAllSaleOrder();

    void deleteSaleOrder(Integer id);

    boolean isSaleCodeExist(String saleCode);

    boolean isSaleCodeExistForEdit(String saleCode, Integer id);
}
