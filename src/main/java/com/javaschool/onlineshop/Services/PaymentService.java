package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.PaymentRequestDTO;
import com.javaschool.onlineshop.Mapper.PaymentMapper;
import com.javaschool.onlineshop.Models.Payment;
import com.javaschool.onlineshop.Repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRequestDTO savePayment(PaymentRequestDTO paymentDTO){
        Payment payment = new Payment();
        payment.setType(paymentDTO.getType());
        payment.setIsDeleted(false);

        paymentRepository.save(payment);
        return createPaymentDTO(payment);
    }

    private PaymentRequestDTO createPaymentDTO(Payment payment){
        return paymentMapper.createPaymentDTO(payment);
    }
}
