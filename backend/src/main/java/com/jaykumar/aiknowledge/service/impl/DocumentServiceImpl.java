package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.entity.Document;
import com.jaykumar.aiknowledge.entity.DocumentChunk;
import com.jaykumar.aiknowledge.repository.DocumentChunkRepository;
import com.jaykumar.aiknowledge.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository documentChunkRepository;
    private final PdfTextExtractor pdfTextExtractor;
    private final TextChunker textChunker;

    @Override
    public Document uploadDocument(MultipartFile file) {

        try {
            // 1. Extract text from PDF
            String extractedText = pdfTextExtractor.extractText(file);

            // 2. Save document metadata
            Document document = Document.builder()
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            Document savedDocument = documentRepository.save(document);

            // 3. Split extracted text into chunks
            List<String> chunks = textChunker.chunkText(extractedText);

            // 4. Create DocumentChunk entities
            List<DocumentChunk> documentChunks =
                    new java.util.ArrayList<>();

            for (int i = 0; i < chunks.size(); i++) {

                DocumentChunk documentChunk = DocumentChunk.builder()
                        .document(savedDocument)
                        .chunkIndex(i)
                        .content(chunks.get(i))
                        .build();

                documentChunks.add(documentChunk);
            }

            // 5. Save all chunks
            documentChunkRepository.saveAll(documentChunks);

            return savedDocument;

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Failed to process PDF document",
                    exception
            );
        }
    }
}