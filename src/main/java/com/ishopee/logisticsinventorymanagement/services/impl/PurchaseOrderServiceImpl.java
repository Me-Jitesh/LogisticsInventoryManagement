package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;
import com.ishopee.logisticsinventorymanagement.repositories.PurchaseOrderRepo;
import com.ishopee.logisticsinventorymanagement.services.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo repo;

    @Override
    public Integer savePurchaseOrder(PurchaseOrder po) {
        return repo.save(po).getId();
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrder() {
        return repo.findAll();
    }

    @Override
    public void deletePurchaseOrder(Integer id) {

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