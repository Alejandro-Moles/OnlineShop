package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderProductsRequestDTO {
    private UUID uuid;
    private UUID orderUUID;
    private UUID productUUID;
    private Integer quantity;
    private Boolean isDeleted;
}
