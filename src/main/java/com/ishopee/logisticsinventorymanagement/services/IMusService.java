package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Mus;

import java.util.List;
import java.util.Map;

public interface IMusService {

    Integer saveMus(Mus mus);

    List<Mus> getAllMus();

    void deleteMus(Integer id);

    Mus getMus(Integer id);

    void updateMus(Mus mus);

    boolean isMusModelCountExist(String musModel);

    boolean isMusModelCountExistForEdit(String musModel, Integer id);

    List<Object[]> getMusTypeAndCount();

    Map<Integer, String> getMusIdAndModel();

}
