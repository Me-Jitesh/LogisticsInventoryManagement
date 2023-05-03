package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Dnp;

import java.util.List;

public interface IDnpService {
    Integer saveDnp(Dnp dnp);

    void updateDnp(Dnp dnp);

    void deleteDnp(Integer id);

    Dnp getOneDnp(Integer id);

    List<Dnp> getAllDnp();

    boolean isDnpExist(Integer id);
}
