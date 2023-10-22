package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryRequestDTO {
    private UUID uuid;
    private String type;
    private boolean isDeleted;
}
