package com.srec.fc.controller;

import com.srec.fc.config.JwtUtil;
import com.srec.fc.dto.*;
import com.srec.fc.entity.User;
import com.srec.fc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getRollNo(), request.getPassword()));

        User user = userRepo.findByRollNo(request.getRollNo()).orElseThrow();
        String token = jwtUtil.generateToken(user.getRollNo(), user.getRole().name());

        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
    }
}