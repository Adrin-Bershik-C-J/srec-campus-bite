package com.srec.fc.service;

import com.srec.fc.config.JwtUtil;
import com.srec.fc.dto.*;
import com.srec.fc.entity.User;
import com.srec.fc.enums.Role;
import com.srec.fc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final OtpService otpService;

    public String registerGuest(GuestRegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .password(encoder.encode(req.getPassword()))
                .role(Role.GUEST)
                .emailVerified(false)
                .build();

        userRepo.save(user);

        otpService.generateAndSendOtp(req.getEmail());

        return "Guest registered. OTP sent to email. Verify to activate account.";
    }

    public String verifyOtp(VerifyOtpRequest req) {
        boolean ok = otpService.verifyOtp(req.getEmail(), req.getCode());
        if (!ok) {
            throw new IllegalArgumentException("Invalid/expired OTP");
        }

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setEmailVerified(true);
        userRepo.save(user);

        return "Email verified. You can now login.";
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByRollNo(req.getIdentifier())
                .or(() -> userRepo.findByEmail(req.getIdentifier()))
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (user.getRole() == Role.GUEST && !Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new BadCredentialsException("Guest email not verified");
        }

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword()));

        String subject = (user.getRollNo() != null && !user.getRollNo().isBlank())
                ? user.getRollNo()
                : user.getEmail();

        String token = jwtUtil.generateToken(subject, user.getRole().name());

        return new AuthResponse(token, user.getRole().name());
    }
}
