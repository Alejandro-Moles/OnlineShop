package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Country;
import com.javaschool.onlineshop.models.Order;
import com.javaschool.onlineshop.models.OrderProducts;
import com.javaschool.onlineshop.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, UUID> {
    boolean existsByOrderAndProduct(Order order, Products products);
}
