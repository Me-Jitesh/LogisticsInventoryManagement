package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderRepo extends JpaRepository<SaleOrder, Integer> {

    @Query("SELECT count(saleCode) FROM SaleOrder  WHERE saleCode=:saleCode")
    Integer isSaleCodeExist(String saleCode);

    @Query("SELECT count(saleCode) FROM SaleOrder  WHERE saleCode=:saleCode AND id !=:id")
    Integer isSaleCodeExistForEdit(String saleCode, Integer id);
}
