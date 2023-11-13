package com.javaschool.onlineshop.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemModel {
    private UUID productUuid;
    private String title;
    private Double price;
    private Integer quantity;
}
