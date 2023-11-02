package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<DeliveryRequestDTO>> getAllDeliveries(){
        List<DeliveryRequestDTO> result = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(result);
    }
}
