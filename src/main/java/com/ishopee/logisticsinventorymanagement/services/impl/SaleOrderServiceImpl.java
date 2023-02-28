package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.SaleOrder;
import com.ishopee.logisticsinventorymanagement.repositories.SaleOrderRepo;
import com.ishopee.logisticsinventorymanagement.services.ISaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

    @Autowired
    private SaleOrderRepo repo;

    @Override
    public Integer saveSaleOrder(SaleOrder so) {
        return repo.save(so).getId();
    }

    @Override
    public SaleOrder getSaleOrderById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("SaleOrder " + id + " Not Exist !"));
    }

    @Override
    public List<SaleOrder> getAllSaleOrder() {
        return repo.findAll();
    }

    @Override
    public void deleteSaleOrder(Integer id) {

    }

    @Override
    public boolean isSaleCodeExist(String saleCode) {
        return repo.isSaleCodeExist(saleCode) > 0;
    }

    @Override
    public boolean isSaleCodeExistForEdit(String saleCode, Integer id) {
        return repo.isSaleCodeExistForEdit(saleCode, id) > 0;
    }
}
