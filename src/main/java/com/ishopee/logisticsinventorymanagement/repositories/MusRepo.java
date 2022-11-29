package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusRepo extends JpaRepository<Mus, Integer> {

    //Register check
    @Query("SELECT count(MUS.musModel) from Mus MUS where MUS.musModel=:musModel")
    Integer getMusModelCount(String musModel);

    //Update Check
    @Query("SELECT count(MUS.musModel) from Mus MUS where MUS.musModel=:musModel and MUS.id !=:id")
    Integer getMusModelCountForEdit(String musModel, Integer id);
}
