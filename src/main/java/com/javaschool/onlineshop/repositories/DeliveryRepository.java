package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryModel, UUID> {
    Optional<DeliveryModel> findByType(String type);

    boolean existsByType(String name);
}
