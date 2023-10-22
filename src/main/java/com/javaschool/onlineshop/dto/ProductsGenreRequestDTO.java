package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductsGenreRequestDTO {
    private UUID uuid;
    private String product_title;
    private String genre_type;
    private Boolean isDeleted;
}
