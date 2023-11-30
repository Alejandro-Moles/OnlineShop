package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.CategoryModel;
import com.javaschool.onlineshop.models.GenreModel;
import com.javaschool.onlineshop.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<GenreModel, UUID> {
    Optional<GenreModel>findByType(String type);
    boolean existsByType(String type);
    List<GenreModel> findByTypeIn(List<String> types);

    List<GenreModel> findAllByIsDeletedFalse();
}
