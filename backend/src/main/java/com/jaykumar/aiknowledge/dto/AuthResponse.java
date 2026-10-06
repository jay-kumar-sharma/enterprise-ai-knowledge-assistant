package com.jaykumar.aiknowledge.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {}