package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.ProductUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductUserTypeRepo extends JpaRepository<ProductUserType, Integer> {

    @Query("SELECT count(userCode) FROM ProductUserType  WHERE userCode=:userCode")
    Integer isUserCodeExist(String userCode);

    @Query("SELECT count(userCode) FROM ProductUserType  WHERE userCode=:userCode AND id !=:id")
    Integer isUserCodeExistForEdit(String userCode, Integer id);

    @Query("SELECT PU.userType,count(PU.userType) from ProductUserType PU group by PU.userType")
    List<Object[]> getProductUserTypeAndCount();

    @Query("SELECT count(userEmail) FROM ProductUserType  WHERE userEmail=:email")
    Integer isEmailExist(String email);

    @Query("SELECT count(userEmail) FROM ProductUserType  WHERE userEmail=:email AND id !=:id")
    Integer isEmailExistForEdit(String email, Integer id);
}
