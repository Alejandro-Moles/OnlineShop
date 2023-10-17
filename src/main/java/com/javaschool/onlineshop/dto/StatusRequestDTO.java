package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class StatusRequestDTO {
    private String type;
    private boolean isDeleted;
}
