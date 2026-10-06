package com.jaykumar.aiknowledge.repository;

import com.jaykumar.aiknowledge.dto.VectorSearchProjection;
import com.jaykumar.aiknowledge.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentChunkRepository
        extends JpaRepository<DocumentChunk, Long> {

    List<DocumentChunk> findByDocumentIdOrderByChunkIndex(Long documentId);

    @Query(value = """
            SELECT
                dc.id AS id,
                dc.document_id AS "documentId",
                dc.chunk_index AS "chunkIndex",
                dc.content AS content,
                (dc.embedding <=> CAST(:embedding AS vector)) AS distance
            FROM document_chunks dc
            WHERE dc.embedding IS NOT NULL
            ORDER BY distance
            LIMIT :limit
            """, nativeQuery = true)
    List<VectorSearchProjection> findSimilarChunksWithDistance(
            @Param("embedding") float[] embedding,
            @Param("limit") int limit
    );
}