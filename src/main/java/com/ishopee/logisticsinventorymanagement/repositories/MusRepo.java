package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusRepo extends JpaRepository<Mus, Integer> {

    //Register check
    @Query("SELECT count(MUS.musModel) from Mus MUS where MUS.musModel=:musModel")
    Integer getMusModelCount(String musModel);

    //Update Check
    @Query("SELECT count(MUS.musModel) from Mus MUS where MUS.musModel=:musModel and MUS.id !=:id")
    Integer getMusModelCountForEdit(String musModel, Integer id);

    // For Chart
    @Query("SELECT MUS.musType,count(MUS.musType) from Mus MUS group by MUS.musType")
    List<Object[]> getMusTypeAndCount();

    @Query("SELECT id,musModel FROM Mus")
    List<Object[]> getMusIdAndModel();
}
