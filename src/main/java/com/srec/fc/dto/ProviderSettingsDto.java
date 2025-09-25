package com.srec.fc.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ProviderSettingsDto {
    private Long id;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean available;
}
