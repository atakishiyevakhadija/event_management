package com.event.management.service.otp;

import com.event.management.exceptions.InvalidOtpException;
import com.event.management.exceptions.OtpExpiredException;
import com.event.management.exceptions.OtpNotFoundException;
import com.event.management.model.entity.Otp;
import com.event.management.repository.OtpRepository;
import com.event.management.service.interfaces.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public void generateAndSendOtp(String email){
        otpRepository.deleteByEmail(email);

        int codeInt = 100000 + random.nextInt(900000);
        String code = String.valueOf(codeInt);

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setExpirationTime(System.currentTimeMillis() + 5 * 60 * 1000);

        otpRepository.save(otp);
        emailService.sendOtp(email, code);
    }

    @Transactional
    public boolean verifyOtp(String email, String inputCode){
        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new OtpNotFoundException("OTP not found!"));

        if (otp.getExpirationTime() < System.currentTimeMillis()){
            throw new OtpExpiredException("OTP expired!");
        }
        if(!otp.getCode().equals(inputCode)){
            throw new InvalidOtpException("Invalid OTP!");
        }
        otpRepository.deleteByEmail(email);

        return true;
    }
}