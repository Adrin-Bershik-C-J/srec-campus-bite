package com.srec.fc.controller;

import com.srec.fc.dto.ApiResponse;
import com.srec.fc.dto.UserDto;
import com.srec.fc.dto.ProviderDto;
import com.srec.fc.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ➕ Add provider (accept DTO instead of Entity)
    @PostMapping("/providers")
    public ResponseEntity<ApiResponse> addProvider(@RequestBody ProviderDto providerDto) {
        return ResponseEntity.ok(adminService.addProvider(providerDto));
    }

    // ❌ Delete provider
    @DeleteMapping("/providers/{id}")
    public ResponseEntity<ApiResponse> deleteProvider(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteProvider(id));
    }

    // ❌ Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

    // ➕ Add user (accept DTO instead of Entity)
    @PostMapping("/users")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(adminService.addUser(userDto));
    }

    // 📋 Get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // 📋 Get all providers
    @GetMapping("/providers")
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        return ResponseEntity.ok(adminService.getAllProviders());
    }
}
