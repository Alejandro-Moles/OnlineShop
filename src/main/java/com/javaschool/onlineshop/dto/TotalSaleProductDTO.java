package com.javaschool.onlineshop.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
public class TotalSaleProductDTO {
    private UUID uuid;
    private String title;
    private String category;
    private String platform;
    private Boolean isDigital;
    private Long totalSold;

    public TotalSaleProductDTO(UUID uuid, String title, String category, String platform, Boolean isDigital, Long totalSold ) {
        this.uuid = uuid;
        this.title = title;
        this.category = category;
        this.platform = platform;
        this.isDigital = isDigital;
        this.totalSold = totalSold;
    }
}
