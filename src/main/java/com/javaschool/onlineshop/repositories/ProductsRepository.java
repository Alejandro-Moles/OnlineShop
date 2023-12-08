package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.dto.TotalSaleProductDTO;
import com.javaschool.onlineshop.models.CategoryModel;
import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository  extends JpaRepository<ProductsModel, UUID> {
    Optional<ProductsModel>findByTitle(String title);
    boolean existsByTitleAndPlatformAndCategoryAndIsDigital(String title, PlatformsModel platforms, CategoryModel category, Boolean isDigital);

    @Query("SELECT new com.javaschool.onlineshop.dto.TotalSaleProductDTO(p.productUuid, p.title, p.category.type, p.platform.type, p.isDigital, SUM(op.quantity) AS totalSold) " +
            "FROM ProductsModel p " +
            "JOIN p.product_orders op " +
            "GROUP BY p.productUuid, p.title, p.category.type, p.platform.type, p.isDigital " +
            "ORDER BY totalSold DESC " +
            "LIMIT 10")
    List<TotalSaleProductDTO> findTop10SoldProducts();

    List<ProductsModel> findByIsDeletedFalseAndStockGreaterThan(Integer stock);

    @Query("SELECT p FROM ProductsModel p " +
            "WHERE p.isDeleted = false AND p.stock > 0 " +
            "ORDER BY (SELECT SUM(op.quantity) FROM OrderProductsModel op WHERE op.product = p) DESC " +
            "LIMIT 10")
    List<ProductsModel> findTop10SoldProductsByStock();
}
