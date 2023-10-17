package com.javaschool.onlineshop.repositories;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaschool.onlineshop.models.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{
    Optional<Category> findByType(String type);

    boolean existsByType(String name);
}
