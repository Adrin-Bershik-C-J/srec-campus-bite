package com.srec.fc.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String code;
}
