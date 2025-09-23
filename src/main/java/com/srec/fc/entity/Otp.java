package com.srec.fc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code; // store as string (e.g. "123456")

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
