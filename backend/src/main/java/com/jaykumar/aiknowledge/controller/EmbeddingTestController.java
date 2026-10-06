package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.service.EmbeddingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/embedding")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class EmbeddingTestController {

    private final EmbeddingService embeddingService;

    @GetMapping
    public Map<String, Object> testEmbedding() {

        String text = "Employees are entitled to annual leave.";

        float[] embedding = embeddingService.generateEmbedding(text);

        return Map.of(
                "text", text,
                "dimensions", embedding.length
        );
    }
}