package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.DeliveryRequestDTO;
import com.javaschool.onlineshop.Services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;


    @PostMapping
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryRequestDTO deliveryDTO) {
        DeliveryRequestDTO result = deliveryService.saveDelivery(deliveryDTO);
        return ResponseEntity.ok("Delivery created : " + result.getType());
    }
}
