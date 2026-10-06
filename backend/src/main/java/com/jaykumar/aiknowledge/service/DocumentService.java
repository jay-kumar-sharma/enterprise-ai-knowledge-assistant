package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document uploadDocument(MultipartFile file);
}