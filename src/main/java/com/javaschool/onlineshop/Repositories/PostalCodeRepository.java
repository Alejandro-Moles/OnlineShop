package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostalCodeRepository extends JpaRepository<PostalCode, UUID> {


    @Query(value = "SELECT a.city_uuid FROM cities a JOIN countries b " +
            "on a.city_country_uuid = b.country_uuid WHERE " +
            "a.city_name = :cityName AND b.country_name = :countryName" , nativeQuery = true)
    UUID findCiudadUuidByCiudadAndPais(@Param("cityName") String cityName, @Param("countryName") String countryName);

    Optional<PostalCode> findByContent(String content);

}
