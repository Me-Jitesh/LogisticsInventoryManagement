package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Mus;

import java.util.List;

public interface IMusService {

    Integer saveMus(Mus mus);

    List<Mus> getAllMus();


}
