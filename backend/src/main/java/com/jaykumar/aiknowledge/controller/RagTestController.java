package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/rag")
@RequiredArgsConstructor
public class RagTestController {

    private final RagService ragService;

    @GetMapping
    public Map<String, String> ask(
            @RequestParam String question) {

        String answer = ragService.ask(question);

        return Map.of(
                "question", question,
                "answer", answer
        );
    }
}