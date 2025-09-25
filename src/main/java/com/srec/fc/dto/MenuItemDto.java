package com.srec.fc.dto;

import lombok.Data;

@Data
public class MenuItemDto {
    private Long id;
    private String name;
    private Double price;
    private Boolean available;
    private Long providerId; // reference only
}
