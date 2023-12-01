package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PaymentRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PaymentMapper;
import com.javaschool.onlineshop.models.PaymentModel;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentServiceTests {
    private final PaymentRepository paymentRepositoryMock = Mockito.mock(PaymentRepository.class);
    private final PaymentMapper paymentMapperMock = Mockito.mock(PaymentMapper.class);
    private final PaymentService paymentService = new PaymentService(paymentRepositoryMock, paymentMapperMock);

    @Test
    void savePayment_ShouldSavePaymentAndReturnPaymentDTO() {
        when(paymentRepositoryMock.existsByType(any())).thenReturn(false);

        when(paymentMapperMock.createPaymentDTO(any())).thenReturn(new PaymentRequestDTO());

        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Credit Card");
        paymentDTO.setIsDeleted(false);

        PaymentRequestDTO savedPaymentDTO = paymentService.savePayment(paymentDTO);

        assertNotNull(savedPaymentDTO);
        verify(paymentRepositoryMock).save(any(PaymentModel.class));
    }

    @Test
    void savePayment_ShouldThrowException_PaymentAlreadyExists() {
        when(paymentRepositoryMock.existsByType(any())).thenReturn(true);

        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Credit Card");
        paymentDTO.setIsDeleted(false);

        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                paymentService.savePayment(paymentDTO));

        assertEquals("Payment already exists", exception.getMessage());

        verify(paymentRepositoryMock, never()).save(any(PaymentModel.class));
    }

    @Test
    void getAllPayment_ShouldReturnListOfPaymentDTOs() {
        when(paymentRepositoryMock.findAll()).thenReturn(Collections.singletonList(new PaymentModel()));

        when(paymentMapperMock.createPaymentDTO(any())).thenReturn(new PaymentRequestDTO());

        List<PaymentRequestDTO> paymentDTOs = paymentService.getAllPayment();

        assertFalse(paymentDTOs.isEmpty());
        verify(paymentRepositoryMock).findAll();
    }

    @Test
    void updatePayment_ShouldUpdatePayment() {
        UUID paymentUUID = UUID.randomUUID();

        when(paymentRepositoryMock.findById(paymentUUID)).thenReturn(Optional.of(new PaymentModel()));

        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Updated Type");
        paymentDTO.setIsDeleted(true);

        assertDoesNotThrow(() -> paymentService.updatePayment(paymentUUID, paymentDTO));

        verify(paymentRepositoryMock).save(any(PaymentModel.class));
    }

    @Test
    void updatePayment_ShouldThrowException_PaymentNotFound() {
        UUID nonExistingPaymentUUID = UUID.randomUUID();

        when(paymentRepositoryMock.findById(nonExistingPaymentUUID)).thenReturn(Optional.empty());

        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Updated Type");
        paymentDTO.setIsDeleted(true);

        NoExistData exception = assertThrows(NoExistData.class, () ->
                paymentService.updatePayment(nonExistingPaymentUUID, paymentDTO));

        assertEquals("Payment don't exist", exception.getMessage());

        verify(paymentRepositoryMock, never()).save(any(PaymentModel.class));
    }

    @Test
    void loadPayment_ShouldReturnPaymentModel() {
        UUID paymentUUID = UUID.randomUUID();

        when(paymentRepositoryMock.findById(paymentUUID)).thenReturn(Optional.of(new PaymentModel()));

        PaymentModel loadedPayment = assertDoesNotThrow(() -> paymentService.loadPayment(paymentUUID));

        assertNotNull(loadedPayment);
        verify(paymentRepositoryMock).findById(paymentUUID);
    }

    @Test
    void loadPayment_ShouldThrowException_PaymentNotFound() {
        UUID nonExistingPaymentUUID = UUID.randomUUID();

        when(paymentRepositoryMock.findById(nonExistingPaymentUUID)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () ->
                paymentService.loadPayment(nonExistingPaymentUUID));

        assertEquals("Payment don't exist", exception.getMessage());

        verify(paymentRepositoryMock).findById(nonExistingPaymentUUID);
    }
}
