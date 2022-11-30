package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.OrderMethod;

import java.util.List;

public interface IOrderMethodService {

    Integer saveOrderMethod(OrderMethod om);

    void updateOrderMethod(OrderMethod om);

    void deleteOrderMethod(Integer id);

    OrderMethod getOrderMethodById(Integer id);

    List<OrderMethod> getAllOrderMethod();

    boolean isOrderCodeExist(String orderCode);

    boolean isOrderCodeExistForEdit(String orderCode, Integer id);
    
}
