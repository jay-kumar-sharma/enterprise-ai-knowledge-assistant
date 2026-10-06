package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.dto.VectorSearchProjection;
import com.jaykumar.aiknowledge.dto.VectorSearchResult;
import com.jaykumar.aiknowledge.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository documentChunkRepository;

    public List<VectorSearchResult> search(String query, int limit) {

        // 1. Generate embedding for the user's query
        float[] embedding =
                embeddingService.generateEmbedding(query);

        // 2. Perform vector similarity search
        List<VectorSearchProjection> results =
                documentChunkRepository.findSimilarChunksWithDistance(
                        embedding,
                        limit
                );

        // 3. Convert projection to DTO
        return results.stream()
                .map(result -> new VectorSearchResult(
                        result.getId(),
                        result.getDocumentId(),
                        result.getChunkIndex(),
                        result.getContent(),
                        result.getDistance()
                ))
                .toList();
    }
}