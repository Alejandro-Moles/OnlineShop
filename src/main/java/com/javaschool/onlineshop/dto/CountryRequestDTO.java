package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CountryRequestDTO {
    private UUID uuid;
    private String name;
    private boolean isDeleted;

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted){
        this.isDeleted = deleted;
    }
}
