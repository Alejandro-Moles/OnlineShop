package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByType(String type);

    boolean existsByType(String name);
}
