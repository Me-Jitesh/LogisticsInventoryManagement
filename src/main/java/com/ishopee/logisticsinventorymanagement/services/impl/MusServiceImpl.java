package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.repositories.MusRepo;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusServiceImpl implements IMusService {

    @Autowired
    private MusRepo musRepo;

    @Override
    public Integer saveMus(Mus mus) {
        mus = musRepo.save(mus);
        return mus.getId();
    }

    @Override
    public List<Mus> getAllMus() {
        return musRepo.findAll();
    }
}
