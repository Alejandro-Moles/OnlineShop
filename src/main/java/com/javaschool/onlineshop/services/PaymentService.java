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

    // Maps a PaymentModel to a PaymentRequestDTO
    private PaymentRequestDTO createPaymentDTO(PaymentModel payment) {
        return paymentMapper.createPaymentDTO(payment);
    }

    // Creates a PaymentModel entity from a PaymentRequestDTO
    private PaymentModel createPaymentEntity(PaymentRequestDTO paymentDTO, PaymentModel payment) {
        payment.setType(paymentDTO.getType());
        payment.setIsDeleted(paymentDTO.getIsDeleted());
        return payment;
    }

    // Saves a PaymentRequestDTO to the database
    @Transactional
    public PaymentRequestDTO savePayment(PaymentRequestDTO paymentDTO) {
        PaymentModel payment = createPaymentEntity(paymentDTO, new PaymentModel());
        if (paymentRepository.existsByType(payment.getType())) {
            throw new ResourceDuplicate("Payment already exists");
        }
        paymentRepository.save(payment);
        return createPaymentDTO(payment);
    }

    // Updates a payment based on UUID
    @Transactional
    public void updatePayment(UUID uuid, PaymentRequestDTO paymentDTO) {
        PaymentModel payment = loadPayment(uuid);
        if (paymentRepository.existsByType(paymentDTO.getType())) {
            throw new ResourceDuplicate("Payment already exists in the database");
        }
        createPaymentEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    // Retrieves all payments from the database
    @Transactional(readOnly = true)
    public List<PaymentRequestDTO> getAllPayment() {
        return paymentRepository.findAll().stream().map(this::createPaymentDTO).toList();
    }

    // Retrieves a payment by UUID
    @Transactional(readOnly = true)
    public PaymentModel loadPayment(UUID uuid) {
        return paymentRepository.findById(uuid).orElseThrow(() -> new NoExistData("Payment doesn't exist"));
    }
}
