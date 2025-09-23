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
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Try rollNo first, then email
        User user = userRepo.findByRollNo(identifier)
                .or(() -> userRepo.findByEmail(identifier))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(identifier) // keep identifier so AuthenticationManager uses same principal
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
