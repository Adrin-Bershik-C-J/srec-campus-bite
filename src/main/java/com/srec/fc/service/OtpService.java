package com.srec.fc.service;

import com.srec.fc.entity.Otp;
import com.srec.fc.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final JavaMailSender mailSender;

    // OTP expiry minutes
    private static final int OTP_TTL_MIN = 5;

    public void generateAndSendOtp(String email) {
        // create 6-digit OTP
        String code = String.format("%06d", new Random().nextInt(1_000_000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_TTL_MIN);

        // remove any previous OTP for this email
        otpRepository.deleteByEmail(email);

        Otp otp = Otp.builder()
                .email(email)
                .code(code)
                .expiresAt(expiresAt)
                .build();

        otpRepository.save(otp);

        // send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your FoodCourt OTP");
        message.setText("Your OTP is: " + code + ". It will expire in " + OTP_TTL_MIN + " minutes.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String code) {
        return otpRepository.findByEmailAndCode(email, code)
                .map(otp -> {
                    if (otp.getExpiresAt().isAfter(LocalDateTime.now())) {
                        // valid
                        otpRepository.deleteByEmail(email); // consume OTP
                        return true;
                    } else {
                        // expired
                        otpRepository.deleteByEmail(email);
                        return false;
                    }
                })
                .orElse(false);
    }
}
