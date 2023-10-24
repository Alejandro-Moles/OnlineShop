package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.models.Delivery;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMapper {
    public DeliveryRequestDTO createDeliveryDTO(Delivery delivery){
        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setUuid(delivery.getDeliveryUuid());
        deliveryDTO.setType(delivery.getType());
        deliveryDTO.setDeleted(delivery.getIsDeleted());

        return deliveryDTO;
    }
}
