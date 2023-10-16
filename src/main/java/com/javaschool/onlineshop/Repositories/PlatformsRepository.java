package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.Platforms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlatformsRepository extends JpaRepository<Platforms, UUID> {
    Optional<Platforms>findByType(String type);
}
