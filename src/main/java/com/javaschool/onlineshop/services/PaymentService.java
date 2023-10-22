package com.javaschool.onlineshop.services;


import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PaymentMapper;
import com.javaschool.onlineshop.models.Payment;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRequestDTO savePayment(PaymentRequestDTO paymentDTO){
        Payment payment = createPaymentEntity(paymentDTO, new Payment());
        if (paymentRepository.existsByType(payment.getType())) {
            throw new ResourceDuplicate("Payment already exists");
        }
        paymentRepository.save(payment);
        return createPaymentDTO(payment);
    }

    private PaymentRequestDTO createPaymentDTO(Payment payment){
        return paymentMapper.createPaymentDTO(payment);
    }

    private Payment createPaymentEntity(PaymentRequestDTO paymentDTO, Payment payment){
        payment.setType(paymentDTO.getType());
        payment.setIsDeleted(false);
        return payment;
    }
}
