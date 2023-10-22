package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.DeliveryMapper;
import com.javaschool.onlineshop.models.Delivery;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryRequestDTO saveDelivery(DeliveryRequestDTO deliveryDTO){
        Delivery delivery = createDeliveryEntity(deliveryDTO, new Delivery());
        if (deliveryRepository.existsByType(delivery.getType())) {
            throw new ResourceDuplicate("Delivery already exists");
        }
        deliveryRepository.save(delivery);
        return createDeliveryDTO(delivery);
    }

    private DeliveryRequestDTO createDeliveryDTO(Delivery delivery){
        return deliveryMapper.createDeliveryDTO(delivery);
    }

    private Delivery createDeliveryEntity(DeliveryRequestDTO deliveryDTO, Delivery delivery){
        delivery.setType(deliveryDTO.getType());
        delivery.setIsDeleted(false);
        return delivery;
    }
}
