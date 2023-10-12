package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.DeliveryRequestDTO;
import com.javaschool.onlineshop.Models.Delivery;
import com.javaschool.onlineshop.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryRequestDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setType(deliveryDTO.getType());
        delivery.setIsDeleted(false);

        deliveryService.saveDelivery(delivery);

        return ResponseEntity.ok("Delivery created with ID: " + delivery.getDelivery_uuid());
    }
}
