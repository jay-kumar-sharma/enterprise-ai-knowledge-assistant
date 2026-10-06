package com.jaykumar.aiknowledge.service.impl;

import com.jaykumar.aiknowledge.entity.Document;
import com.jaykumar.aiknowledge.repository.DocumentRepository;
import com.jaykumar.aiknowledge.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DoucmentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    @Override
    public Document uploadDocument(MultipartFile file) {

        Document document= Document.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .build();


        return documentRepository.save(document);
    }
}
