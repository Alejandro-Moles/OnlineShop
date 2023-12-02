package com.javaschool.onlineshop.dto;

import lombok.Data;
@Data
public class UserStatisticsDTO {
    private long totalOrders;
    private double totalSpent;
    private long totalProductsBought;

    public UserStatisticsDTO(long totalOrders, double totalSpent, long totalProductsBought) {
        this.totalOrders = totalOrders;
        this.totalSpent = totalSpent;
        this.totalProductsBought = totalProductsBought;
    }

    public UserStatisticsDTO() {

    }
}
