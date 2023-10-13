package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class CityRequestDTO {
    private String name;
    private boolean isDeleted;

    private String countryName;
}
