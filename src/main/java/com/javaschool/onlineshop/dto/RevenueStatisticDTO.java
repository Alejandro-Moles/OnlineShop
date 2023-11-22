package com.javaschool.onlineshop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RevenueStatisticDTO {
    private Date date;
    private Double totalRevenue;

    public RevenueStatisticDTO(Date date, Double totalRevenue) {
        this.date = date;
        this.totalRevenue = totalRevenue;
    }
}
