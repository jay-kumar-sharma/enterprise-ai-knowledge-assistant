package com.jaykumar.aiknowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public String generateAnswer(String prompt) {

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}