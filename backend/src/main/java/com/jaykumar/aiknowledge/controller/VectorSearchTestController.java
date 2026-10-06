package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.entity.DocumentChunk;
import com.jaykumar.aiknowledge.service.VectorSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/vector-search")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class VectorSearchTestController {

    private final VectorSearchService vectorSearchService;

    @GetMapping
    public List<Map<String, Object>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit) {

        List<DocumentChunk> chunks =
                vectorSearchService.search(query, limit);

        List<Map<String, Object>> results = new ArrayList<>();

        for (DocumentChunk chunk : chunks) {

            Map<String, Object> result = new HashMap<>();

            result.put("id", chunk.getId());
            result.put("documentId", chunk.getDocument().getId());
            result.put("chunkIndex", chunk.getChunkIndex());
            result.put("content", chunk.getContent());

            results.add(result);
        }

        return results;
    }
}