package com.drive.EvaluationProject;

import com.drive.EvaluationProject.controller.DocumentController;
import com.drive.EvaluationProject.modal.Document;
import com.drive.EvaluationProject.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentController.class)
@WithMockUser(roles = "ADMIN") // Add if security is enabled
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Test
    void testUploadDocument() throws Exception {
        Document mockDoc = new Document();
        mockDoc.setTitle("test.pdf");
        when(documentService.processDocument(any()))
            .thenReturn(CompletableFuture.completedFuture(mockDoc));

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.pdf",
            MediaType.APPLICATION_PDF_VALUE,
            "Test Content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/documents")
                .file(file))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("test.pdf"));
    }

    @Test
    void testSearchDocuments() throws Exception {
        Document doc = new Document();
        doc.setContent("Spring Boot");
        when(documentService.searchByKeyword("Spring"))
            .thenReturn(Collections.singletonList(doc));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/search")
                .param("keyword", "Spring"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].content").value("Spring Boot"));
    }

    @Test
    void testFilterDocuments() throws Exception {
        Document doc = new Document();
        doc.setAuthor("John Doe");
        Pageable pageable = PageRequest.of(0, 10);
        
        // Mock service response
        when(documentService.filterDocuments(any(), any(), any()))
            .thenReturn(Collections.singletonList(doc));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/filter")
                .param("author", "John Doe")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].author").value("John Doe"));
    }

    @Test
    void testFilterDocumentsWithDate() throws Exception {
        Document doc = new Document();
        doc.setCreatedDate(LocalDate.now());
        Pageable pageable = PageRequest.of(0, 10);

        when(documentService.filterDocuments(any(), any(), any()))
            .thenReturn(Collections.singletonList(doc));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/documents/filter")
                .param("date", LocalDate.now().toString())
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].createdDate").exists());
    }
}