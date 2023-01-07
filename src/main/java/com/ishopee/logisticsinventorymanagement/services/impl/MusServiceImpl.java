package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.MusNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.Mus;
import com.ishopee.logisticsinventorymanagement.repositories.MusRepo;
import com.ishopee.logisticsinventorymanagement.services.IMusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void deleteMus(Integer id) {
//        musRepo.deleteById(id);
        musRepo.delete(getMus(id));
    }

    @Override
    public Mus getMus(Integer id) {
        return musRepo.findById(id).orElseThrow(() -> new MusNotFoundException("MUS " + id + " Not Exist !"));
    }

    @Override
    public void updateMus(Mus mus) {
        musRepo.save(mus);
    }

    @Override
    public boolean isMusModelCountExist(String musModel) {
        Integer count = musRepo.getMusModelCount(musModel);
        boolean flag = count > 0 ? true : false;
        return flag;
    }

    @Override
    public boolean isMusModelCountExistForEdit(String musModel, Integer id) {
        return musRepo.getMusModelCountForEdit(musModel, id) > 0;
    }

    @Override
    public List<Object[]> getMusTypeAndCount() {
        return musRepo.getMusTypeAndCount();
    }

    @Override
    public Map<Integer, String> getMusIdAndModel() {
        List<Object[]> list = musRepo.getMusIdAndModel();

//        Map<Integer, String> muses = new LinkedHashMap<>();
//        for (Object[] ob : list) {
//            muses.put((Integer) ob[0], (String) ob[1]);
//        }

//       After Java 8
        return list.stream().collect(Collectors.toMap((ob) -> (Integer) ob[0], (ob) -> (String) ob[1]));
    }
}
