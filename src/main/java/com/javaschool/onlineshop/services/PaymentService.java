package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PaymentMapper;
import com.javaschool.onlineshop.models.PaymentModel;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private PaymentRequestDTO createPaymentDTO(PaymentModel payment){
        return paymentMapper.createPaymentDTO(payment);
    }

    private PaymentModel createPaymentEntity(PaymentRequestDTO paymentDTO, PaymentModel payment){
        payment.setType(paymentDTO.getType());
        payment.setIsDeleted(paymentDTO.getIsDeleted());
        return payment;
    }

    @Transactional
    public PaymentRequestDTO savePayment(PaymentRequestDTO paymentDTO){
        PaymentModel payment = createPaymentEntity(paymentDTO, new PaymentModel());
        if (paymentRepository.existsByType(payment.getType())) {
            throw new ResourceDuplicate("Payment already exists");
        }
        paymentRepository.save(payment);
        return createPaymentDTO(payment);
    }

    @Transactional
    public void updatePayment(UUID uuid, PaymentRequestDTO paymentDTO){
        PaymentModel payment = loadPayment(uuid);
        createPaymentEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentRequestDTO> getAllPayment(){
        return paymentRepository.findAll().stream().map(this::createPaymentDTO).toList();
    }

    @Transactional(readOnly = true)
    public PaymentModel loadPayment(UUID uuid){
        return paymentRepository.findById(uuid).orElseThrow(() -> new NoExistData("Payment don't exist"));
    }
}
