package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.City;
import com.javaschool.onlineshop.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
    Optional<City> findByName(String name);

    @Query(value = "SELECT a.city_uuid FROM cities a JOIN countries b " +
            "on a.city_country_uuid = b.country_uuid WHERE " +
            "a.city_name = :cityName AND b.country_name = :countryName" , nativeQuery = true)
    UUID findCityUuidByCityAndCountry(@Param("cityName") String cityName, @Param("countryName") String countryName);

    boolean existsByNameAndCountry(String name, Country country);
}
