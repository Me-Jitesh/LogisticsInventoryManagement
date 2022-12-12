package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.OrderMethodNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.OrderMethod;
import com.ishopee.logisticsinventorymanagement.repositories.OrderMethodRepo;
import com.ishopee.logisticsinventorymanagement.services.IOrderMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

    @Autowired
    private OrderMethodRepo repo;

    @Override
    public Integer saveOrderMethod(OrderMethod om) {
        return repo.save(om).getId();
    }

    @Override
    public void updateOrderMethod(OrderMethod om) {
        if (om.getId() == null || !repo.existsById(om.getId()))
            throw new OrderMethodNotFoundException("Order Method Does Not Exist !");
        repo.save(om);
    }

    @Override
    public void deleteOrderMethod(Integer id) {
        repo.delete(getOrderMethodById(id));
    }

    @Override
    public OrderMethod getOrderMethodById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new OrderMethodNotFoundException("Order Method Does Not Exist !"));
    }

    @Override
    public List<OrderMethod> getAllOrderMethod() {
        return repo.findAll();
    }

    @Override
    public boolean isOrderCodeExist(String orderCode) {
        return repo.isOrderCodeExist(orderCode) > 0;
    }

    @Override
    public boolean isOrderCodeExistForEdit(String orderCode, Integer id) {
        return repo.isOrderCodeExistForEdit(orderCode, id) > 0;
    }
}