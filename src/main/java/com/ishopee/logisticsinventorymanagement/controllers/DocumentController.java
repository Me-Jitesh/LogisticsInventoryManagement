package com.ishopee.logisticsinventorymanagement.controllers;

import com.ishopee.logisticsinventorymanagement.models.Document;
import com.ishopee.logisticsinventorymanagement.services.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
        Long id = System.currentTimeMillis();
        model.addAttribute("id", id);
        return "Document";
    }

    @PostMapping("/upload")
    public String saveDocument(@RequestParam Long docId, MultipartFile docData) {
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

    @GetMapping("/delete")
    public String saveDocument(@RequestParam Long docId) {

        try {
            service.deleteDocument(docId);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "redirect:all";
    }

    @GetMapping("/download")
    public void downloadDocument(@RequestParam Long docId, HttpServletResponse resp) {
        try {
            Document document = service.getDocument(docId);
            resp.setHeader("Content-Disposition", "attachment;filename=" + document.getDocName());
            // Copy Data From Document to Response
            FileCopyUtils.copy(document.getDocData(), resp.getOutputStream());

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }
}