package com.drive.EvaluationProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drive.EvaluationProject.modal.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    @Query("SELECT d FROM Document d WHERE " +
           "d.content ILIKE %:keyword% OR " +
           "d.title ILIKE %:keyword%")
    List<Document> searchByKeyword(String keyword);
    
    @Query("SELECT d FROM Document d WHERE d.author = :author")
    List<Document> findByAuthor(@Param("author") String author, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE d.author = :author AND d.createdDate = :date")
    List<Document> findByAuthorAndCreatedDate(
        @Param("author") String author, 
        @Param("date") LocalDate date, 
        Pageable pageable
    );
    
    Page<Document> findAll(Pageable pageable);
}
