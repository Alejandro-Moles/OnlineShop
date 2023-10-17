package com.javaschool.onlineshop.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String type;
    private boolean isDeleted;
}
