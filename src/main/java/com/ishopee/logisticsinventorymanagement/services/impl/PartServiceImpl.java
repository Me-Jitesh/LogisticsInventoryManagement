package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.exceptions.PartNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.Part;
import com.ishopee.logisticsinventorymanagement.repositories.PartRepo;
import com.ishopee.logisticsinventorymanagement.services.IPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements IPartService {

    @Autowired
    private PartRepo repo;

    @Override
    public Integer savePart(Part part) {
        return repo.save(part).getId();
    }

    @Override
    public void updatePart(Part part) {
        if (part.getId() == null || !repo.existsById(part.getId()))
            throw new PartNotFoundException("Part Does Not Exist");
        repo.save(part);
    }

    @Override
    public void deletePart(Integer id) {
        repo.delete(getOnePart(id));
    }

    @Override
    public List<Part> getAllParts() {
        return repo.findAll();
    }

    @Override
    public Part getOnePart(Integer id) {
        return repo.findById(id).orElseThrow(() -> new PartNotFoundException("Part" + id + "not exist !"));
    }
}
