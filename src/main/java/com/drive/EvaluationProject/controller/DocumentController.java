package com.drive.EvaluationProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.drive.EvaluationProject.modal.Document;
import com.drive.EvaluationProject.service.DocumentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Upload document
    @PostMapping
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(documentService.processDocument(file).join());
    }

    // Search documents by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDocuments(
            @RequestParam("keyword") String keyword
    ) {
        List<Document> documents = documentService.searchByKeyword(keyword);
        return ResponseEntity.ok(documents);
    }

    // Filter documents by metadata (author/date) with pagination
    @GetMapping("/filter")
    public ResponseEntity<List<Document>> filterDocuments(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "date", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable // Automatically handles page, size, sort
    ) {
        List<Document> documents = documentService.filterDocuments(author, date, pageable);
        return ResponseEntity.ok(documents);
    }
}