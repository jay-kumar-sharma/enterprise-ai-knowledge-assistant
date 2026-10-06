package com.jaykumar.aiknowledge.controller;

import com.jaykumar.aiknowledge.service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/chat")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ChatTestController {

    private final ChatService chatService;

    @GetMapping
    public Map<String, String> chat(
            @RequestParam String prompt) {

        String answer = chatService.generateAnswer(prompt);

        return Map.of(
                "prompt", prompt,
                "answer", answer
        );
    }
}