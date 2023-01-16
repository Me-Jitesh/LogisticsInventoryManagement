package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Document;

import java.util.List;

public interface IDocumentService {

    void saveDocument(Document doc);

    List<Object[]> getDocumentIdAndName();

    void deleteDocument(Long docId);

    Document getDocument(Long docId);
}