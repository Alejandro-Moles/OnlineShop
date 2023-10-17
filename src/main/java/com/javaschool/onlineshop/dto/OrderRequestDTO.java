package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderRequestDTO {
    private String payment;
    private String status;
    private String delivery;

    //User Address
    private String apartament;
    private String home;
    private String street;

    private String mail;
    private Boolean payStatus;
    private Boolean isDeleted;
    private Date orderDate;

}
