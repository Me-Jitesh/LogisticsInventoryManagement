package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.Dnp;
import com.ishopee.logisticsinventorymanagement.repositories.DnpRepo;
import com.ishopee.logisticsinventorymanagement.services.IDnpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DnpServiceImpl implements IDnpService {

    @Autowired
    private DnpRepo repo;

    @Override
    public Integer saveDnp(Dnp dnp) {
        return repo.save(dnp).getId();
    }

    @Override
    public void updateDnp(Dnp dnp) {
        repo.save(dnp);
    }

    @Override
    public void deleteDnp(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Dnp getOneDnp(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Dnp> getAllDnp() {
        return repo.findAll();
    }

    @Override
    public boolean isDnpExist(Integer id) {
        return repo.existsById(id);
    }
}
