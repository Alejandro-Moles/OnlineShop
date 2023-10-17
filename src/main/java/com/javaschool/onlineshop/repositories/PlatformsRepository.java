package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Platforms;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface PlatformsRepository extends JpaRepository<Platforms, UUID> {
    Optional<Platforms>findByType(String type);

    boolean existsByType(String name);
}
