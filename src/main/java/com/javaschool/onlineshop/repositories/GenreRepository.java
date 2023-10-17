package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Optional<Genre>findByType(String type);

    boolean existsByType(String type);
}
