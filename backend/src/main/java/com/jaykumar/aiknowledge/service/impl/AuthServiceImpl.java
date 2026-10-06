package com.jaykumar.aiknowledge.service.impl;

import com.jaykumar.aiknowledge.dto.AuthResponse;
import com.jaykumar.aiknowledge.dto.LoginRequest;
import com.jaykumar.aiknowledge.dto.RegisterRequest;
import com.jaykumar.aiknowledge.entity.Role;
import com.jaykumar.aiknowledge.entity.User;
import com.jaykumar.aiknowledge.repository.UserRepository;
import com.jaykumar.aiknowledge.security.JwtService;
import com.jaykumar.aiknowledge.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return new AuthResponse(
                token,
                "Bearer"
        );
    }
}