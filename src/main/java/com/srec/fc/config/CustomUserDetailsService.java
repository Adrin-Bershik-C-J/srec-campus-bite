package com.srec.fc.config;

import com.srec.fc.entity.User;
import com.srec.fc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String rollNo) throws UsernameNotFoundException {
        User user = userRepo.findByRollNo(rollNo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getRollNo())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
