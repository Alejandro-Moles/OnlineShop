package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.CategoryRequestDTO;
import com.javaschool.onlineshop.DTO.PaymentRequestDTO;
import com.javaschool.onlineshop.Models.Category;
import com.javaschool.onlineshop.Models.Payment;
import com.javaschool.onlineshop.Services.CategoryService;
import com.javaschool.onlineshop.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setType(paymentDTO.getType());
        payment.setIsDeleted(false);

        paymentService.savePayment(payment);

        return ResponseEntity.ok("Payment created with ID: " + payment.getPayment_uuid());
    }
}
