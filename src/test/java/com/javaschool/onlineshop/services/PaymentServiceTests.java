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

    /**
     * Test for saving a payment when the payment does not already exist, checking if it successfully saves the payment and returns a DTO.
     */
    @Test
    void savePayment_ShouldSavePaymentAndReturnPaymentDTO() {
        // Mocking data
        when(paymentRepositoryMock.existsByType(any())).thenReturn(false);
        when(paymentMapperMock.createPaymentDTO(any())).thenReturn(new PaymentRequestDTO());

        // Creating a payment DTO
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Credit Card");
        paymentDTO.setIsDeleted(false);

        // Calling the service method
        PaymentRequestDTO savedPaymentDTO = paymentService.savePayment(paymentDTO);

        // Assertions
        assertNotNull(savedPaymentDTO);
        verify(paymentRepositoryMock).save(any(PaymentModel.class));
    }

    /**
     * Test for saving a payment when the payment already exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void savePayment_ShouldThrowException_PaymentAlreadyExists() {
        // Mocking data
        when(paymentRepositoryMock.existsByType(any())).thenReturn(true);

        // Creating a payment DTO
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Credit Card");
        paymentDTO.setIsDeleted(false);

        // Assertions
        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                paymentService.savePayment(paymentDTO));

        assertEquals("Payment already exists", exception.getMessage());

        verify(paymentRepositoryMock, never()).save(any(PaymentModel.class));
    }

    /**
     * Test for getting all payments, checking if it returns a list of PaymentDTOs.
     */
    @Test
    void getAllPayment_ShouldReturnListOfPaymentDTOs() {
        // Mocking data
        when(paymentRepositoryMock.findAll()).thenReturn(Collections.singletonList(new PaymentModel()));
        when(paymentMapperMock.createPaymentDTO(any())).thenReturn(new PaymentRequestDTO());

        // Calling the service method
        List<PaymentRequestDTO> paymentDTOs = paymentService.getAllPayment();

        // Assertions
        assertFalse(paymentDTOs.isEmpty());
        verify(paymentRepositoryMock).findAll();
    }

    /**
     * Test for updating a payment, checking if it successfully updates the payment.
     */
    @Test
    void updatePayment_ShouldUpdatePayment() {
        // Mocking data
        UUID paymentUUID = UUID.randomUUID();
        when(paymentRepositoryMock.findById(paymentUUID)).thenReturn(Optional.of(new PaymentModel()));

        // Creating a payment DTO for update
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Updated Type");
        paymentDTO.setIsDeleted(true);

        // Assertions
        assertDoesNotThrow(() -> paymentService.updatePayment(paymentUUID, paymentDTO));

        verify(paymentRepositoryMock).save(any(PaymentModel.class));
    }

    /**
     * Test for updating a payment that does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void updatePayment_ShouldThrowException_PaymentNotFound() {
        // Mocking data
        UUID nonExistingPaymentUUID = UUID.randomUUID();
        when(paymentRepositoryMock.findById(nonExistingPaymentUUID)).thenReturn(Optional.empty());

        // Creating a payment DTO for update
        PaymentRequestDTO paymentDTO = new PaymentRequestDTO();
        paymentDTO.setType("Updated Type");
        paymentDTO.setIsDeleted(true);

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () ->
                paymentService.updatePayment(nonExistingPaymentUUID, paymentDTO));

        assertEquals("Payment doesn't exist", exception.getMessage());

        verify(paymentRepositoryMock, never()).save(any(PaymentModel.class));
    }

    /**
     * Test for loading a payment, checking if it returns the correct PaymentModel.
     */
    @Test
    void loadPayment_ShouldReturnPaymentModel() {
        // Mocking data
        UUID paymentUUID = UUID.randomUUID();
        when(paymentRepositoryMock.findById(paymentUUID)).thenReturn(Optional.of(new PaymentModel()));

        // Calling the service method
        PaymentModel loadedPayment = assertDoesNotThrow(() -> paymentService.loadPayment(paymentUUID));

        // Assertions
        assertNotNull(loadedPayment);
        verify(paymentRepositoryMock).findById(paymentUUID);
    }

    /**
     * Test for loading a payment that does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadPayment_ShouldThrowException_PaymentNotFound() {
        // Mocking data
        UUID nonExistingPaymentUUID = UUID.randomUUID();
        when(paymentRepositoryMock.findById(nonExistingPaymentUUID)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () ->
                paymentService.loadPayment(nonExistingPaymentUUID));

        assertEquals("Payment doesn't exist", exception.getMessage());

        verify(paymentRepositoryMock).findById(nonExistingPaymentUUID);
    }
}
