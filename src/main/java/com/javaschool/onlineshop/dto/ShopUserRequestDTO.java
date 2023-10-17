package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ShopUserRequestDTO {
    private String userRol;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private Date birth;
    private Boolean isDeleted;
}
