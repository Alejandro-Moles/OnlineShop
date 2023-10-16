package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class ProductsGenreRequestDTO {
    private String product_title;
    private String genre_type;
    private Boolean isDeleted;
}
