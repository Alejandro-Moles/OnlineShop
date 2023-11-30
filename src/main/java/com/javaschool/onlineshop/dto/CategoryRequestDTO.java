package com.javaschool.onlineshop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequestDTO {

    private UUID uuid;
    private String type;
    private boolean isDeleted;

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted){
        this.isDeleted = deleted;
    }
}
