package com.softline.api.service;

import com.softline.api.dto.LoginRequest;
import com.softline.api.dto.LoginResponse;
import com.softline.api.repository.UserRepository;
import com.softline.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByUsername(request.username())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos"));

        String token = jwtService.generate(user.getUsername(), user.isAdmin());
        return new LoginResponse(token, user.getUsername(), user.isAdmin());
    }
}
