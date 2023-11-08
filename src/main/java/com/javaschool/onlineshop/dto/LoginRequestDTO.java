package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String mail;
    private String password;
}
