package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updatePayment(@PathVariable UUID uuid, @RequestBody PaymentRequestDTO paymentDTO){
        paymentService.updatePayment(uuid, paymentDTO);
        return ResponseEntity.ok("Payment changed succesfully");
    }
}
