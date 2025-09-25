package com.srec.fc.service;

import com.srec.fc.dto.*;
import com.srec.fc.entity.User;
import com.srec.fc.entity.Provider;
import com.srec.fc.repository.UserRepository;
import com.srec.fc.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;

    public AdminService(UserRepository userRepository, ProviderRepository providerRepository) {
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }

    // ✅ Add new provider from DTO
    public ApiResponse addProvider(ProviderDto providerDto) {
        Provider provider = new Provider();
        provider.setProviderName(providerDto.getProviderName());
        provider.setContact(providerDto.getContact());
        // link user if required
        if (providerDto.getUserId() != null) {
            userRepository.findById(providerDto.getUserId())
                    .ifPresent(provider::setUser);
        }
        Provider saved = providerRepository.save(provider);
        return new ApiResponse(true, "Provider added successfully", new ProviderDto(saved));
    }

    // ✅ Delete provider
    @Transactional
    public ApiResponse deleteProvider(Long providerId) {
        if (providerRepository.existsById(providerId)) {
            providerRepository.deleteById(providerId);
            return new ApiResponse(true, "Provider deleted successfully");
        }
        return new ApiResponse(false, "Provider not found");
    }

    // ✅ Delete a user (student/teacher/guest)
    @Transactional
    public ApiResponse deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new ApiResponse(true, "User deleted successfully");
        }
        return new ApiResponse(false, "User not found");
    }

    // ✅ Add student/teacher login from DTO
    public ApiResponse addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getUsername()); // maps DTO.username → entity.name
        user.setRollNo(userDto.getRollNumber()); // maps DTO.rollNumber → entity.rollNo
        user.setRole(userDto.getRole());
        user.setPassword("default123"); // ⚠️ consider encoding + changing on first login
        User saved = userRepository.save(user);
        return new ApiResponse(true, "User added successfully", new UserDto(saved));
    }

    // ✅ Get all users
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // ✅ Get all providers
    public List<ProviderDto> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(ProviderDto::new)
                .collect(Collectors.toList());
    }
}
