package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.DeliveryRequestDTO;
import com.javaschool.onlineshop.Mapper.DeliveryMapper;
import com.javaschool.onlineshop.Models.Delivery;
import com.javaschool.onlineshop.Repositories.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryRequestDTO saveDelivery(DeliveryRequestDTO deliveryDTO){
        Delivery delivery = new Delivery();
        delivery.setType(deliveryDTO.getType());
        delivery.setIsDeleted(false);

        deliveryRepository.save(delivery);
        return createDeliveryDTO(delivery);
    }

    private DeliveryRequestDTO createDeliveryDTO(Delivery delivery){
        return deliveryMapper.createDeliveryDTO(delivery);
    }
}
