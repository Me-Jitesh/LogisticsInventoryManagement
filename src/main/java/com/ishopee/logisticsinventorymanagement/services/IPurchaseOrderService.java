package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.PurchaseDetails;
import com.ishopee.logisticsinventorymanagement.models.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService {

    Integer savePurchaseOrder(PurchaseOrder po);

    PurchaseOrder getPurchaseOrderById(Integer id);

    List<PurchaseOrder> getAllPurchaseOrder();

    void deletePurchaseOrder(Integer id);

    boolean isOrderCodeExist(String orderCode);

    boolean isOrderCodeExistForEdit(String orderCode, Integer id);

    Integer savePurchaseOrderDetails(PurchaseDetails pdtl);

    List<PurchaseDetails> getPurchaseDetailsByPoId(Integer poId);

    void deletePurchaseDetail(Integer pdtlId);

    String getCurrentPoStatus(Integer poId);

    void updatePoStatus(Integer poId, String newStatus);

    Integer getPurchaseDetailsCountByPoId(Integer poId);

}
