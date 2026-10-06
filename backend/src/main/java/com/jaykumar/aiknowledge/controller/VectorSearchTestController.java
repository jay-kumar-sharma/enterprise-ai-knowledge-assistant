package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.dto.VectorSearchResult;
import com.jaykumar.aiknowledge.service.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/vector-search")
@RequiredArgsConstructor
public class VectorSearchTestController {

    private final VectorSearchService vectorSearchService;

    @GetMapping
    public List<Map<String, Object>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit) {

        List<VectorSearchResult> searchResults =
                vectorSearchService.search(query, limit);

        List<Map<String, Object>> results = new ArrayList<>();

        for (VectorSearchResult result : searchResults) {

            Map<String, Object> response = new HashMap<>();

            response.put("id", result.id());
            response.put("documentId", result.documentId());
            response.put("chunkIndex", result.chunkIndex());
            response.put("distance", result.distance());
            response.put("content", result.content());

            results.add(response);
        }

        return results;
    }
}