package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository  extends JpaRepository<ProductsModel, UUID> {
    Optional<ProductsModel>findByTitle(String title);

    boolean existsByTitleAndPlatform(String title, PlatformsModel platforms);
}
