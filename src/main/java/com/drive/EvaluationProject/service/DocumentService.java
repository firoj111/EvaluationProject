package com.drive.EvaluationProject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.drive.EvaluationProject.modal.Document;
import com.drive.EvaluationProject.repository.DocumentRepository;


@Service
public class DocumentService {
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Async
    public CompletableFuture<Document> processDocument(MultipartFile file) {
        // Implement file processing logic here
        Document doc = new Document();
        doc.setTitle(file.getOriginalFilename());
        doc.setContent("Sample content"); // Replace with actual extraction
        return CompletableFuture.completedFuture(documentRepository.save(doc));
    }
	
	public List<Document> searchByKeyword(String keyword) {
	    return documentRepository.searchByKeyword(keyword);
	}

	public List<Document> filterDocuments(String author, LocalDate date, Pageable pageable) {
	    if (author != null && date != null) {
	        return documentRepository.findByAuthorAndCreatedDate(author, date, pageable);
	    } else if (author != null) {
	        return documentRepository.findByAuthor(author, pageable);
	    } else {
	        return documentRepository.findAll(pageable).getContent();
	    }
	}
}
