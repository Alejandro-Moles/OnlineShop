package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.dto.UserStatisticsDTO;
import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ShopUserRepository extends JpaRepository<ShopUserModel, UUID> {
    Optional<ShopUserModel> findByMail(String mail);
    boolean existsByMail(String name);

    @Query("SELECT NEW com.javaschool.onlineshop.dto.UserStatisticsDTO(" +
            "COUNT(DISTINCT o.orderUuid), " +
            "SUM(op.quantity * p.price), " +
            "SUM(op.quantity) " + // Cambio aquí para obtener la suma de la cantidad de productos comprados
            ") " +
            "FROM ShopUserModel su " +
            "LEFT JOIN su.orders o " +
            "LEFT JOIN o.order_product op " +
            "LEFT JOIN op.product p " +
            "WHERE su.userUuid = :userUuid " +
            "GROUP BY su.userUuid, su.name, su.surname")
    UserStatisticsDTO getUserStatistics(@Param("userUuid") UUID userUuid);

}
