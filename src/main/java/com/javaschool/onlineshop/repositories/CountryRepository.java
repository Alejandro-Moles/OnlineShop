package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.CountryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryModel, UUID> {
    Optional<CountryModel> findByName(String name);

    boolean existsByName(String name);
}
