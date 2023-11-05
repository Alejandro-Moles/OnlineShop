package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GenreRequestDTO {
    private UUID uuid;
    private String type;
    private Boolean isDeleted;

}
