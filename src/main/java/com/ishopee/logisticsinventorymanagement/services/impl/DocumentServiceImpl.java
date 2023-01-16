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

    @Override
    public void deleteDocument(Long docId) {
        if (repo.existsById(docId)) {
            repo.deleteById(docId);
        } else {
            throw new RuntimeException("Document Does Not Exist");
        }
    }

    @Override
    public Document getDocument(Long docId) {
        return repo.findById(docId).orElseThrow(() -> new RuntimeException("Document Does Not Exist"));
    }
}