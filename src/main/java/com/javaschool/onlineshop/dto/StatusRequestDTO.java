package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusRequestDTO {
    private UUID uuid;
    private String type;
    private boolean isDeleted;

    public StatusRequestDTO(UUID statusUuid, String type) {
    }

    public StatusRequestDTO() {

    }
}
