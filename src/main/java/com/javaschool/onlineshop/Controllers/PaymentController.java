package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.PaymentRequestDTO;
import com.javaschool.onlineshop.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
