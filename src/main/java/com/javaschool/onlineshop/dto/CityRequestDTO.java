package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class CityRequestDTO {
    private String name;
    private boolean isDeleted;
    private String countryName;
}
