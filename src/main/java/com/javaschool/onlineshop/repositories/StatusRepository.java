package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<StatusModel, UUID> {
    Optional<StatusModel> findByType(String type);

    boolean existsByType(String name);
}
