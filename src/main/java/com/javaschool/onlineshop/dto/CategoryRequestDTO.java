package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class CategoryRequestDTO {
    private String type;
    private boolean isDeleted;
}
