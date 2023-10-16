package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class UserAddressRequestDTO {
    private String apartament;
    private String home;
    private String street;
    private Boolean isDeleted;
    private String postalCode;
    private String userMail;

}
