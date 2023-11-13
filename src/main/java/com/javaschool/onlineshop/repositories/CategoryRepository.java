package com.javaschool.onlineshop.repositories;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.onlineshop.models.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID>{
    Optional<CategoryModel> findByType(String type);

    boolean existsByType(String name);
}
