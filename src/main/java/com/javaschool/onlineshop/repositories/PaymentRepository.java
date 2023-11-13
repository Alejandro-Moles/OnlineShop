package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {
    Optional<PaymentModel> findByType(String type);

    boolean existsByType(String name);
}
