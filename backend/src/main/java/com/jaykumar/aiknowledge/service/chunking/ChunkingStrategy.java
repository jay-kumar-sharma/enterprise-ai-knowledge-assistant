package com.jaykumar.aiknowledge.service.chunking;

import java.util.List;

public interface ChunkingStrategy {

    List<String> chunk(String text);
}