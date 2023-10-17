package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Delivery;
import com.javaschool.onlineshop.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    Optional<Delivery> findByType(String type);

    boolean existsByType(String name);
}
