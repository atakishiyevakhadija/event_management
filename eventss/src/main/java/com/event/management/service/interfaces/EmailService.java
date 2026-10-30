package com.event.management.service.interfaces;

public interface EmailService {
    void sendOtp(String to, String otp);
}