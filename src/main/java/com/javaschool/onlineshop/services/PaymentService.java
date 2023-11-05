package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PaymentMapper;
import com.javaschool.onlineshop.models.Payment;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        payment.setIsDeleted(paymentDTO.getIsDeleted());
        return payment;
    }

    public void updatePayment(UUID uuid, PaymentRequestDTO paymentDTO){
        Payment payment = loadPayment(uuid);
        createPaymentEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentRequestDTO> getAllPayment(){
        return paymentRepository.findAll().stream().map(this::createPaymentDTO).toList();
    }

    @Transactional(readOnly = true)
    private Payment loadPayment(UUID uuid){
        return paymentRepository.findById(uuid).orElseThrow(() -> new NoExistData("Payment don't exist"));
    }
}
