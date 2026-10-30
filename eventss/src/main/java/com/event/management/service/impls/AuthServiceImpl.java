package com.event.management.service.impls;

import com.event.management.exceptions.*;
import com.event.management.model.dto.auth.AuthResponse;
import com.event.management.model.dto.auth.LoginRequest;
import com.event.management.model.dto.auth.RegisterRequest;
import com.event.management.model.entity.RefreshToken;
import com.event.management.model.entity.User;
import com.event.management.model.enums.Role;
import com.event.management.repository.RefreshTokenRepository;
import com.event.management.repository.UserRepository;
import com.event.management.service.interfaces.AuthService;
import com.event.management.service.jwt.JwtService;
import com.event.management.service.otp.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final RefreshTokenRepository tokenRepository;

    @Override
    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByFullName(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        Role role = registerRequest.getRole() == null ? Role.USER : registerRequest.getRole();

        User user = User.builder()
                .fullName(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .enabled(false)
                .build();

        userRepository.save(user);

        otpService.generateAndSendOtp(user.getEmail());

        return "User Created! Verify your account.";
    }

    @Transactional
    @Override
    public String verifyOtp(String email, String code) {
        otpService.verifyOtp(email, code);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        user.setEnabled(true);
        userRepository.save(user);
        return "Account verified!";
    }

    @Transactional
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByFullName(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Invalid password");
        }

        if (!user.isEnabled()) {
            throw new AccountNotVerifiedException("Account not verified!");
        }

        tokenRepository.deleteByUser(user);

        String accessToken = jwtService.generateToken(user.getFullName(), user.getRole().name());
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(user);
        token.setExpirationTime(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION);
        token.setRevoked(false);

        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }
}