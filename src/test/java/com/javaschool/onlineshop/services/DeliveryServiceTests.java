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

    @Test
    void saveDelivery_WhenDeliveryDoesNotExist_ShouldSaveDelivery() {
        when(deliveryMapperMock.createDeliveryDTO(any())).thenReturn(new DeliveryRequestDTO());
        when(deliveryRepositoryMock.existsByType(any())).thenReturn(false);

        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setType("New Delivery");
        deliveryDTO.setUuid(UUID.randomUUID());
        deliveryDTO.setIsDeleted(false);
        DeliveryRequestDTO savedDeliveryDTO = deliveryService.saveDelivery(deliveryDTO);

        assertNotNull(savedDeliveryDTO);
    }

    @Test
    void saveDelivery_WhenDeliveryExists_ShouldThrowResourceDuplicateException() {
        when(deliveryRepositoryMock.existsByType(any())).thenReturn(true);

        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setType("Existing Delivery");

        assertThrows(ResourceDuplicate.class, () -> deliveryService.saveDelivery(deliveryDTO));
    }

    @Test
    void updateDelivery_WhenDeliveryExists_ShouldUpdateDelivery() {
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

        deliveryService.updateDelivery(existingDeliveryUUID, updatedDeliveryDTO);

        verify(deliveryRepositoryMock).save(any(DeliveryModel.class));

        assertEquals("Updated Delivery", existingDelivery.getType());
    }

    @Test
    void loadDelivery_WhenDeliveryExists_ShouldReturnDelivery() {
        UUID existingDeliveryUUID = UUID.randomUUID();

        DeliveryModel existingDelivery = new DeliveryModel();
        existingDelivery.setDeliveryUuid(existingDeliveryUUID);
        existingDelivery.setType("Existing Delivery");
        existingDelivery.setIsDeleted(false);

        when(deliveryRepositoryMock.findById(existingDeliveryUUID)).thenReturn(Optional.of(existingDelivery));

        DeliveryModel loadedDelivery = deliveryService.loadDelivery(existingDeliveryUUID);

        assertEquals(existingDelivery, loadedDelivery);
    }

    @Test
    void loadDelivery_WhenDeliveryDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentDeliveryUUID = UUID.randomUUID();

        when(deliveryRepositoryMock.findById(nonExistentDeliveryUUID)).thenReturn(Optional.empty());

        assertThrows(NoExistData.class, () -> deliveryService.loadDelivery(nonExistentDeliveryUUID));
    }
}
