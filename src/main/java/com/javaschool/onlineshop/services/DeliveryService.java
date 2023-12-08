package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.DeliveryMapper;
import com.javaschool.onlineshop.models.DeliveryModel;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    // Injecting the DeliveryRepository for database operations
    private final DeliveryRepository deliveryRepository;

    // Injecting the DeliveryMapper for mapping between DTO and entity
    private final DeliveryMapper deliveryMapper;

    // This method creates a DeliveryDTO from a DeliveryModel
    private DeliveryRequestDTO createDeliveryDTO(DeliveryModel delivery) {
        return deliveryMapper.createDeliveryDTO(delivery);
    }

    // This method updates a DeliveryModel entity with data from a DeliveryDTO
    private DeliveryModel createDeliveryEntity(DeliveryRequestDTO deliveryDTO, DeliveryModel delivery) {
        delivery.setType(deliveryDTO.getType());
        delivery.setIsDeleted(deliveryDTO.getIsDeleted());
        return delivery;
    }

    // This method updates an existing delivery in the database
    @Transactional
    public void updateDelivery(UUID uuid, DeliveryRequestDTO deliveryDTO) {
        // Loading the existing delivery from the database
        DeliveryModel delivery = loadDelivery(uuid);

        if (deliveryRepository.existsByType(deliveryDTO.getType())) {
            throw new ResourceDuplicate("Delivery already exists in the database");
        }

        // Updating the delivery entity with data from the DTO
        createDeliveryEntity(deliveryDTO, delivery);

        // Saving the updated delivery to the database
        deliveryRepository.save(delivery);
    }

    // This method retrieves all deliveries from the database
    @Transactional(readOnly = true)
    public List<DeliveryRequestDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(this::createDeliveryDTO).toList();
    }

    // This method loads a delivery by its UUID from the database
    @Transactional(readOnly = true)
    public DeliveryModel loadDelivery(UUID uuid) {
        return deliveryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Delivery doesn't exist"));
    }

    // This method saves a new delivery to the database
    @Transactional
    public DeliveryRequestDTO saveDelivery(DeliveryRequestDTO deliveryDTO) {
        // Creating a DeliveryModel entity from the DTO
        DeliveryModel delivery = createDeliveryEntity(deliveryDTO, new DeliveryModel());

        // Checking if a delivery with the same type already exists
        if (deliveryRepository.existsByType(delivery.getType())) {
            throw new ResourceDuplicate("Delivery already exists");
        }

        // Saving the delivery to the database
        deliveryRepository.save(delivery);

        // Returning the saved delivery as a DTO
        return createDeliveryDTO(delivery);
    }
}
