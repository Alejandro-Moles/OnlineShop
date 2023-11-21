package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.OrderProductsModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    List<OrderModel> findOrderByShopUser(ShopUserModel shopUserModel);
}
