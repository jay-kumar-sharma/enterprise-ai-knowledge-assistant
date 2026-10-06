package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.entity.DocumentChunk;
import com.jaykumar.aiknowledge.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository documentChunkRepository;

    public List<DocumentChunk> search(String query, int limit) {

        float[] embedding =
                embeddingService.generateEmbedding(query);

        return documentChunkRepository.findSimilarChunks(
                embedding,
                limit
        );
    }
}