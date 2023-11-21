package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class NewPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
