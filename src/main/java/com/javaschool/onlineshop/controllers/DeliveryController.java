package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    // Endpoint to create a new delivery
    @PostMapping
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryRequestDTO deliveryDTO) {
        DeliveryRequestDTO result = deliveryService.saveDelivery(deliveryDTO);
        return ResponseEntity.ok("Delivery created : " + result.getType());
    }

    // Endpoint to get all deliveries
    @GetMapping
    public ResponseEntity<List<DeliveryRequestDTO>> getAllDeliveries() {
        List<DeliveryRequestDTO> result = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update an existing delivery by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateDelivery(@PathVariable UUID uuid, @RequestBody DeliveryRequestDTO deliveryDTO) {
        deliveryService.updateDelivery(uuid, deliveryDTO);
        return ResponseEntity.ok("Delivery changed successfully");
    }
}
