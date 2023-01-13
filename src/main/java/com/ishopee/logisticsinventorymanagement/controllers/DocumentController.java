package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Document;
import com.ishopee.logisticsinventorymanagement.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private IDocumentService service;

    @GetMapping("/all")
    public String showDocument(Model model) {
        List<Object[]> list = service.getDocumentIdAndName();
        model.addAttribute("list", list);
        return "Document";
    }

    @PostMapping("/upload")
    public String saveDocument(@RequestParam Integer docId, MultipartFile docData) {
        Document document = new Document();
        try {
            document.setDocId(docId);
            document.setDocName(docData.getOriginalFilename());
            document.setDocData(docData.getBytes());
            service.saveDocument(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:all";
    }
}