package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class PostalCodeRequestDTO {
    private String content;
    private boolean isDeleted;
    private String cityName;
    private String countryName;
}
