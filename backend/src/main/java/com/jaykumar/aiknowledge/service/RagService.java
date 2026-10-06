package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.dto.VectorSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorSearchService vectorSearchService;
    private final ChatService chatService;

    public String ask(String question) {

        // 1. Retrieve relevant chunks
        List<VectorSearchResult> results =
                vectorSearchService.search(question, 5);

        // 2. Build context from retrieved results
        String context = results.stream()
                .map(VectorSearchResult::content)
                .collect(Collectors.joining("\n\n"));

        // 3. Build RAG prompt
        String prompt = """
                You are an AI knowledge assistant.

                Answer the user's question using only the information
                provided in the context below.

                If the answer cannot be found in the context,
                say:
                "I could not find this information in the knowledge base."

                Do not invent, assume, or use outside information.

                Context:
                %s

                User Question:
                %s
                """.formatted(context, question);

        // 4. Generate answer
        return chatService.generateAnswer(prompt);
    }
}