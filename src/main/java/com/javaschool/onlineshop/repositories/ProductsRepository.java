package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.dto.TotalSaleProductDTO;
import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository  extends JpaRepository<ProductsModel, UUID> {
    Optional<ProductsModel>findByTitle(String title);
    boolean existsByTitleAndPlatform(String title, PlatformsModel platforms);

    @Query("SELECT new com.javaschool.onlineshop.dto.TotalSaleProductDTO(p.productUuid, p.title, SUM(op.quantity) AS totalSold) " +
            "FROM ProductsModel p " +
            "JOIN p.product_orders op " +
            "GROUP BY p.productUuid, p.title " +
            "ORDER BY totalSold DESC " +
            "LIMIT 10")
    List<TotalSaleProductDTO> findTop10SoldProducts();
}
