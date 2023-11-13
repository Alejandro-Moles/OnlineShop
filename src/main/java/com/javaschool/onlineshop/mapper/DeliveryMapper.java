package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.DeliveryRequestDTO;
import com.javaschool.onlineshop.models.DeliveryModel;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMapper {
    public DeliveryRequestDTO createDeliveryDTO(DeliveryModel delivery){
        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setUuid(delivery.getDeliveryUuid());
        deliveryDTO.setType(delivery.getType());
        deliveryDTO.setIsDeleted(delivery.getIsDeleted());

        return deliveryDTO;
    }
}
