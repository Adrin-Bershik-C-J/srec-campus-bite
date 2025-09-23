package com.srec.fc.entity;

import com.srec.fc.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // college roll number (nullable for guests)
    @Column(nullable = true, unique = true)
    private String rollNo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // guest email (nullable for college users)
    @Column(unique = true)
    private String email;

    // OTP verification flag for guests
    private Boolean emailVerified;

    // optional google id for OAuth2 logins (nullable)
    private String googleId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    // provider profile if this user is a provider (1:1)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Provider providerProfile;
}
