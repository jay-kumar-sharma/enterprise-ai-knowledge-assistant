package com.jaykumar.aiknowledge.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/test")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminTestController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> adminEndpoint() {

        return Map.of(
                "message", "Admin authorization successful"
        );
    }
}