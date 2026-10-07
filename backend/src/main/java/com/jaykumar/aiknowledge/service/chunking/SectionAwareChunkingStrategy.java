package com.jaykumar.aiknowledge.service.chunking;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SectionAwareChunkingStrategy implements ChunkingStrategy {

    private static final Pattern SECTION_PATTERN =
            Pattern.compile("^\\s*\\d+[.)]\\s+.+$");

    @Override
    public List<String> chunk(String text) {

        List<String> sections = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return sections;
        }

        String[] lines = text.split("\\R");

        StringBuilder currentSection = new StringBuilder();

        for (String line : lines) {

            if (SECTION_PATTERN.matcher(line).matches()) {

                if (!currentSection.isEmpty()) {
                    sections.add(currentSection.toString().trim());
                    currentSection.setLength(0);
                }
            }

            if (!line.isBlank()) {
                currentSection.append(line).append("\n");
            }
        }

        if (!currentSection.isEmpty()) {
            sections.add(currentSection.toString().trim());
        }

        return sections;
    }
}