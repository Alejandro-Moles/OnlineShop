package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PlatformsModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface PlatformsRepository extends JpaRepository<PlatformsModel, UUID> {
    Optional<PlatformsModel>findByType(String type);

    boolean existsByType(String name);
}
