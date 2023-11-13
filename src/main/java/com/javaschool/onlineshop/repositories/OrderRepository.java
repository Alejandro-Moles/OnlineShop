package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
}
