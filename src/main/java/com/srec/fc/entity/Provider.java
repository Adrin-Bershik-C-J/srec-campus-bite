package com.srec.fc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "providers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;
    private String contact;

    // Link back to the User (login credentials)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;

    @OneToOne(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProviderSettings settings;
}
