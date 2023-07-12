package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.MusNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.repositories.MusRepo;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import com.ishopee.logisticsinventorymanagement.utilities.MyAppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MusServiceImpl implements IMusService {

    @Autowired
    private MusRepo repo;

    @Override
    public Integer saveMus(Mus mus) {
        mus = repo.save(mus);
        return mus.getId();
    }

    @Override
    public List<Mus> getAllMus() {
        return repo.findAll();
    }

    @Override
    public void deleteMus(Integer id) {
//        musRepo.deleteById(id);
        repo.delete(getMus(id));
    }

    @Override
    public Mus getMus(Integer id) {
        return repo.findById(id).orElseThrow(() -> new MusNotFoundException("MUS " + id + " Not Exist !"));
    }

    @Override
    public void updateMus(Mus mus) {
        if (mus.getId() == null || !repo.existsById(mus.getId())) {
            throw new MusNotFoundException("MUS DOES NOT EXIST ID " + mus.getId());
        } else {
            repo.save(mus);
        }
    }

    @Override
    public boolean isMusModelCountExist(String musModel) {
        Integer count = repo.getMusModelCount(musModel);
        boolean flag = count > 0 ? true : false;
        return flag;
    }

    @Override
    public boolean isMusModelCountExistForEdit(String musModel, Integer id) {
        return repo.getMusModelCountForEdit(musModel, id) > 0;
    }

    @Override
    public List<Object[]> getMusTypeAndCount() {
        return repo.getMusTypeAndCount();
    }

    @Override
    public Map<Integer, String> getMusIdAndModel() {
        return MyAppUtility.convertListIntoMap(repo.getMusIdAndModel());
    }
}
