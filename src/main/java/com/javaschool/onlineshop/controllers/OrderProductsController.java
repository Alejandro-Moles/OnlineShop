package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.services.OrderProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{uuid}")
    public ResponseEntity<List<OrderProductsRequestDTO>> getOrdersProductbyUuid(@PathVariable UUID uuid){
        List<OrderProductsRequestDTO> result = orderProductsService.findAllOrdersProductByOrder(uuid);
        return ResponseEntity.ok(result);
    }
}
