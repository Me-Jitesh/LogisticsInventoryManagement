package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.Document;
import com.ishopee.logisticsinventorymanagement.repositories.DocumentRepo;
import com.ishopee.logisticsinventorymanagement.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentRepo repo;

    @Override
    public void saveDocument(Document doc) {
        repo.save(doc);
    }

    @Override
    public List<Object[]> getDocumentIdAndName() {
        return repo.getDocumentIdAndName();
    }
}