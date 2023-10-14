package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.City;
import com.javaschool.onlineshop.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    Optional<City> findByName(String name);
    boolean existsByNameAndCountry(String name, Country country);
}
