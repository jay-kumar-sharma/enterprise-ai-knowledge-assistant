package com.jaykumar.aiknowledge.service;

import com.jaykumar.aiknowledge.dto.AuthResponse;
import com.jaykumar.aiknowledge.dto.LoginRequest;
import com.jaykumar.aiknowledge.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}