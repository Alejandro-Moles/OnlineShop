package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository  extends JpaRepository<Products, UUID> {
    Optional<Products>findByTitle(String title);

    boolean existsByTitle(String title);
}
