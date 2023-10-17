package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByName(String name);

    boolean existsByName(String name);
}
