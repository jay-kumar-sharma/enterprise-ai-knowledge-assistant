package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.entity.Document;
import com.jaykumar.aiknowledge.service.DocumentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Document> uploadDocument(
            @RequestPart("file") MultipartFile file) {

        Document document = documentService.uploadDocument(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(document);
    }
}