package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class StatusRequestDTO {
    private String type;
    private boolean isDeleted;
}
