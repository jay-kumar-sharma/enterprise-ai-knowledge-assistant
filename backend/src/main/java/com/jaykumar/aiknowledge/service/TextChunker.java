package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.service.chunking.ChunkingStrategy;
import com.jaykumar.aiknowledge.service.chunking.SectionAwareChunkingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextChunker {

    private final SectionAwareChunkingStrategy chunkingStrategy;

    public List<String> chunkText(String text) {
        return chunkingStrategy.chunk(text);
    }
}