package com.srec.fc.dto;

import lombok.Data;

@Data
public class LoginRequest {
    // identifier can be rollNo (for students/providers) or email (for guests)
    private String identifier;
    private String password;
}
