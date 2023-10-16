package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository  extends JpaRepository<Products, UUID> {
    Optional<Products>findByTitle(String title);
}
