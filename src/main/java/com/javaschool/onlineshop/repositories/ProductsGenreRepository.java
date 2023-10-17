package com.javaschool.onlineshop.repositories;
import com.javaschool.onlineshop.models.Genre;
import com.javaschool.onlineshop.models.Products;
import com.javaschool.onlineshop.models.ProductsGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsGenreRepository extends JpaRepository<ProductsGenre, UUID> {
    boolean existsByProductAndGenre(Products products, Genre genre);
}
