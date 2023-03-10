package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartRepo extends JpaRepository<Part, Integer> {
    //Register check
    @Query("SELECT count(part.partCode) from Part part where part.partCode=:partCode")
    Integer getPartCodeCount(String partCode);

    //Update Check
    @Query("SELECT count(part.partCode) from Part part where part.partCode=:partCode and part.id !=:id")
    Integer getPartCodeCountForEdit(String partCode, Integer id);

    @Query("SELECT id,partCode from  Part")
    List<Object[]> getPartIdAndCode();
}
