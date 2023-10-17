package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.services.OrderProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordersProducts")
@RequiredArgsConstructor
public class OrderProductsController {
    private final OrderProductsService orderProductsService;

    @PostMapping
    public ResponseEntity<String> createOrderProducts(@RequestBody OrderProductsRequestDTO orderProductsDTO) {
        OrderProductsRequestDTO result = orderProductsService.saveOrderProducts(orderProductsDTO);
        return ResponseEntity.ok("Order Products created for: " + result.getOrderUUID());
    }
}
