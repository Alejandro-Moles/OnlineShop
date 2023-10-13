package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.DeliveryRequestDTO;
import com.javaschool.onlineshop.Models.Delivery;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMapper {
    public DeliveryRequestDTO createDeliveryDTO(Delivery delivery){
        DeliveryRequestDTO deliveryDTO = new DeliveryRequestDTO();
        deliveryDTO.setType(delivery.getType());
        deliveryDTO.setDeleted(delivery.getIsDeleted());

        return deliveryDTO;
    }
}
