package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<CityModel, UUID> {
    Optional<CityModel> findByName(String name);

    @Query(value = "SELECT a.city_uuid FROM cities a JOIN countries b " +
            "on a.city_country_uuid = b.country_uuid WHERE " +
            "a.city_name = :cityName AND b.country_name = :countryName" , nativeQuery = true)
    UUID findCityUuidByCityAndCountry(@Param("cityName") String cityName, @Param("countryName") String countryName);

    boolean existsByNameAndCountry(String name, CountryModel country);
}
