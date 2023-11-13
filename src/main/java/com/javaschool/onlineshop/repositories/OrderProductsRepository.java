package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.OrderProductsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductsRepository extends JpaRepository<OrderProductsModel, UUID> {
    boolean existsByOrderAndProduct(OrderModel order, ProductsModel products);
}
