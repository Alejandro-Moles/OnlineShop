package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostalCodeRequestDTO {
    private UUID uuid;
    private String content;
    private boolean isDeleted;
    private String cityName;
    private String countryName;

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted){
        this.isDeleted = deleted;
    }
}
