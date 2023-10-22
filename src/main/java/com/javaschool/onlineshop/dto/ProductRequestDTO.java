package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequestDTO {
    private UUID uuid;
    private String category;
    private String platform;
    private String title;
    private Double price;
    private Double weight;
    private Integer stock;
    private Integer pegi;
    private Boolean isDigital;
    private String description;
    private String image;
    private Boolean isDeleted;
}
