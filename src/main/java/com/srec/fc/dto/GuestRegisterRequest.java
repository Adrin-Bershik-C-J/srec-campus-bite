package com.srec.fc.dto;

import lombok.Data;

@Data
public class GuestRegisterRequest {
    private String email;
    private String password;
    private String name;
}
