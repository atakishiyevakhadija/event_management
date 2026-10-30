package com.event.management.service.interfaces;


import com.event.management.model.dto.auth.AuthResponse;
import com.event.management.model.dto.auth.LoginRequest;
import com.event.management.model.dto.auth.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    String verifyOtp(String email, String code);
    AuthResponse login(LoginRequest loginRequest);
}