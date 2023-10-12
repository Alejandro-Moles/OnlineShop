package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.Models.Payment;
import com.javaschool.onlineshop.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public void savePayment(Payment payment){
        paymentRepository.save(payment);
    }


}
