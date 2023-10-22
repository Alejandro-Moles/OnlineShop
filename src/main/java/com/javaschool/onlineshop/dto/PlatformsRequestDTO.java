package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlatformsRequestDTO {
    private UUID uuid;
    private String type;
    private boolean isDeleted;
}
