package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.dto.RevenueStatisticDTO;
import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    List<OrderModel> findOrderByShopUser(ShopUserModel shopUserModel);

    @Query("SELECT NEW com.javaschool.onlineshop.dto.RevenueStatisticDTO(o.date, SUM(op.quantity * p.price)) " +
            "FROM OrderModel o " +
            "JOIN o.order_product op " +
            "JOIN op.product p " +
            "WHERE o.date >= :startDate AND o.date <= :endDate " +
            "GROUP BY o.date")
    List<RevenueStatisticDTO> getRevenueStatistics(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
