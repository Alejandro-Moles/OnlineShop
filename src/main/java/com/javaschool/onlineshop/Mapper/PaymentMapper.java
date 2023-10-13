package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.PaymentRequestDTO;
import com.javaschool.onlineshop.Models.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public PaymentRequestDTO createPaymentDTO(Payment payment){
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType(payment.getType());
        paymentDTO.setDeleted(payment.getIsDeleted());

        return paymentDTO;
    }
}
