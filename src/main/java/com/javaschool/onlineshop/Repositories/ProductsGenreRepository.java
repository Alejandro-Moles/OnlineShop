package com.javaschool.onlineshop.Repositories;
import com.javaschool.onlineshop.Models.Country;
import com.javaschool.onlineshop.Models.Genre;
import com.javaschool.onlineshop.Models.Products;
import com.javaschool.onlineshop.Models.ProductsGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsGenreRepository extends JpaRepository<ProductsGenre, UUID> {
    boolean existsByProductAndGenre(Products products, Genre genre);
}
