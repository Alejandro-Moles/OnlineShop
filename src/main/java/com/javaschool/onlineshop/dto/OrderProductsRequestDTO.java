package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderProductsRequestDTO {
    private UUID orderUUID;
    private String productTitle;
    private Integer quantity;
    private Boolean isDeleted;
}
