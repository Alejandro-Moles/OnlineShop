package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CountryRequestDTO {
    private UUID uuid;
    private String name;
    private boolean isDeleted;
}
