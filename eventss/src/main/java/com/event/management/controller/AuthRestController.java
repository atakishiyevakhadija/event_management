package com.event.management.controller;

import com.event.management.model.dto.auth.AuthResponse;
import com.event.management.model.dto.auth.LoginRequest;
import com.event.management.model.dto.auth.RegisterRequest;
import com.event.management.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth APIs", description = "Authentication APIs")
public class AuthRestController {
    private final AuthService authService;

    public AuthRestController(@Valid AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }
    @PostMapping("/verify-email")
    public String verify(@Valid @RequestParam String email,
                         @RequestParam String code){
        return authService.verifyOtp(email, code);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}