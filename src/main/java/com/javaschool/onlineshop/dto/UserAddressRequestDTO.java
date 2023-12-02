package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserAddressRequestDTO {
    private UUID uuid;
    private String apartament;
    private String home;
    private String street;
    private Boolean isDeleted;
    private String postalCode;
    private String userMail;

    public UserAddressRequestDTO(UUID addressUuid, String apartament, String street, String home) {
    }

    public UserAddressRequestDTO() {

    }
}
