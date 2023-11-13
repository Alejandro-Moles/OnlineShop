package com.javaschool.onlineshop.repositories;
import com.javaschool.onlineshop.models.GenreModel;
import com.javaschool.onlineshop.models.ProductsModel;
import com.javaschool.onlineshop.models.ProductsGenreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsGenreRepository extends JpaRepository<ProductsGenreModel, UUID> {
    boolean existsByProductAndGenre(ProductsModel products, GenreModel genre);
}
