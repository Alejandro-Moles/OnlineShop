package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentDTO) {
        PaymentRequestDTO result = paymentService.savePayment(paymentDTO);
        return ResponseEntity.ok("Payment created : " + result.getType());
    }

    @GetMapping
    public ResponseEntity<List<PaymentRequestDTO>> getAllPayment(){
        List<PaymentRequestDTO> result = paymentService.getAllPayment();
        return ResponseEntity.ok(result);
    }
}
