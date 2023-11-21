package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(){
        List<OrderRequestDTO> result = orderService.getAllOrders();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderDTO) {
        OrderRequestDTO result = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderRequestDTO> getOrderbyUuid(@PathVariable UUID uuid){
        OrderRequestDTO result = orderService.getOrderUuid(uuid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<OrderRequestDTO>> getOrderbyShopUser(@PathVariable UUID userUuid){
        System.out.println(userUuid);
        List<OrderRequestDTO> result = orderService.getAllOrdersForUser(userUuid);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{uuid}/{status}")
    public ResponseEntity<String> updateOrder(@PathVariable UUID uuid, @PathVariable String status){
        orderService.updateOrder(uuid, status);
        return ResponseEntity.ok("Order changed succesfully");
    }
}
