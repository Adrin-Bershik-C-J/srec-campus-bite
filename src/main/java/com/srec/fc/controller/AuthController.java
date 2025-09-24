package com.srec.fc.controller;

import com.srec.fc.dto.*;
import com.srec.fc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/guest-register")
    public ResponseEntity<String> guestRegister(@RequestBody GuestRegisterRequest req) {
        try {
            return ResponseEntity.ok(authService.registerGuest(req));
        } catch (Exception e) {
            e.printStackTrace(); // log real cause
            return ResponseEntity.status(500).body("Unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest req) {
        return ResponseEntity.ok(authService.verifyOtp(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    // TODO: add forget-password endpoint later
}
