package com.jaykumar.aiknowledge.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
@SecurityRequirement(name = "Bearer Authentication")
public class TestController {

    @GetMapping("/protected")
    public Map<String, String> protectedEndpoint(
            Authentication authentication) {

        return Map.of(
                "message", "JWT authentication successful",
                "username", authentication.getName()
        );
    }
}