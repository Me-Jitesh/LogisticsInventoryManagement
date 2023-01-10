package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Part;

import java.util.List;

public interface IPartService {
    Integer savePart(Part part);

    void updatePart(Part part);

    void deletePart(Integer id);

    List<Part> getAllParts();

    Part getOnePart(Integer id);

    boolean isPartCodeExist(String partCode);

    boolean isPartCodeExistForEdit(String partCode, Integer id);
}
