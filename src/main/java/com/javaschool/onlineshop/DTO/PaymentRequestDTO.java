package com.javaschool.onlineshop.DTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String type;
    private boolean isDeleted;
}