package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
    boolean existsByApartamentAndStreetAndUserAndHome(String apartament,String street, ShopUser user, String home);

    Optional<UserAddress> findByApartamentAndHomeAndStreet(String apartament, String home, String street);
}
