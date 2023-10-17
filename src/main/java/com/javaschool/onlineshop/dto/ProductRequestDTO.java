package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
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
