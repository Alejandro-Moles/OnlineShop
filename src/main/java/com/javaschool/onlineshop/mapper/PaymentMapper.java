package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.models.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public PaymentRequestDTO createPaymentDTO(Payment payment){
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setUuid(payment.getPaymentUuid());
        paymentDTO.setType(payment.getType());
        paymentDTO.setDeleted(payment.getIsDeleted());

        return paymentDTO;
    }
}
