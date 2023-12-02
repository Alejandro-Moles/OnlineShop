package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.DeliveryMapper;
import com.javaschool.onlineshop.models.DeliveryModel;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeliveryServiceTests {
    private final DeliveryRepository deliveryRepositoryMock = Mockito.mock(DeliveryRepository.class);
    private final DeliveryMapper deliveryMapperMock = Mockito.mock(DeliveryMapper.class);
    private final DeliveryService deliveryService = new DeliveryService(deliveryRepositoryMock, deliveryMapperMock);

    /**
     * Test for saving a delivery when the delivery does not exist, checking if it successfully saves the delivery.
     */
    @Test
    void saveDelivery_WhenDeliveryDoesNotExist_ShouldSaveDelivery() {
        // Mocking data
        when(deliveryMapperMock.createDeliveryDTO(any())).thenReturn(new DeliveryRequestDTO());
        when(deliveryRepositoryMock.existsByType(any())).thenReturn(false);

        // Creating a DeliveryDTO
        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setType("New Delivery");
        deliveryDTO.setUuid(UUID.randomUUID());
        deliveryDTO.setIsDeleted(false);

        // Calling the service method
        DeliveryRequestDTO savedDeliveryDTO = deliveryService.saveDelivery(deliveryDTO);

        // Assertions
        assertNotNull(savedDeliveryDTO);
    }

    /**
     * Test for saving a delivery when the delivery exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveDelivery_WhenDeliveryExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(deliveryRepositoryMock.existsByType(any())).thenReturn(true);

        // Creating a DeliveryDTO
        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setType("Existing Delivery");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> deliveryService.saveDelivery(deliveryDTO));
    }

    /**
     * Test for updating a delivery when the delivery exists, checking if it successfully updates the delivery.
     */
    @Test
    void updateDelivery_WhenDeliveryExists_ShouldUpdateDelivery() {
        // Mocking data
        UUID existingDeliveryUUID = UUID.randomUUID();
        DeliveryRequestDTO updatedDeliveryDTO = new DeliveryRequestDTO();
        updatedDeliveryDTO.setType("Updated Delivery");
        updatedDeliveryDTO.setUuid(existingDeliveryUUID);
        updatedDeliveryDTO.setIsDeleted(false);

        DeliveryModel existingDelivery = new DeliveryModel();
        existingDelivery.setDeliveryUuid(existingDeliveryUUID);
        existingDelivery.setType("Existing Delivery");
        existingDelivery.setIsDeleted(false);

        when(deliveryRepositoryMock.findById(existingDeliveryUUID)).thenReturn(Optional.of(existingDelivery));

        // Calling the service method
        deliveryService.updateDelivery(existingDeliveryUUID, updatedDeliveryDTO);

        // Verifying the save method was called
        verify(deliveryRepositoryMock).save(any(DeliveryModel.class));

        // Assertions
        assertEquals("Updated Delivery", existingDelivery.getType());
    }

    /**
     * Test for loading a delivery when the delivery exists, checking if it returns the loaded delivery.
     */
    @Test
    void loadDelivery_WhenDeliveryExists_ShouldReturnDelivery() {
        // Mocking data
        UUID existingDeliveryUUID = UUID.randomUUID();
        DeliveryModel existingDelivery = new DeliveryModel();
        existingDelivery.setDeliveryUuid(existingDeliveryUUID);
        existingDelivery.setType("Existing Delivery");
        existingDelivery.setIsDeleted(false);

        when(deliveryRepositoryMock.findById(existingDeliveryUUID)).thenReturn(Optional.of(existingDelivery));

        // Calling the service method
        DeliveryModel loadedDelivery = deliveryService.loadDelivery(existingDeliveryUUID);

        // Assertions
        assertEquals(existingDelivery, loadedDelivery);
    }

    /**
     * Test for loading a delivery when the delivery does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadDelivery_WhenDeliveryDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentDeliveryUUID = UUID.randomUUID();
        when(deliveryRepositoryMock.findById(nonExistentDeliveryUUID)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> deliveryService.loadDelivery(nonExistentDeliveryUUID));
    }

}
