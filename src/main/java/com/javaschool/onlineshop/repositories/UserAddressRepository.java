package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.models.UserAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddressModel, UUID> {
    boolean existsByApartamentAndStreetAndUserAndHome(String apartament, String street, ShopUserModel user, String home);

    Optional<UserAddressModel> findByApartamentAndHomeAndStreet(String apartament, String home, String street);
}
