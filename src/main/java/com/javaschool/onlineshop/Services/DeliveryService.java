package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.Models.Delivery;
import com.javaschool.onlineshop.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public void saveDelivery(Delivery delivery){
        deliveryRepository.save(delivery);
    }
}
