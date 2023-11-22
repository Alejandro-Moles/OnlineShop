package com.javaschool.onlineshop.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
public class TotalSaleProductDTO {
    private UUID uuid;
    private String title;
    private Long totalSold;

    public TotalSaleProductDTO(UUID uuid, String title, Long totalSold) {
        this.uuid = uuid;
        this.title = title;
        this.totalSold = totalSold;
    }
}
