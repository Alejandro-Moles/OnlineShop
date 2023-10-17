package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO orderDTO) {
        OrderRequestDTO result = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok("Order created for: " + result.getMail());
    }
}
