package com.jaykumar.aiknowledge.dto;

public record VectorSearchResult(
        Long id,
        Long documentId,
        Integer chunkIndex,
        String content,
        double distance
) {
}