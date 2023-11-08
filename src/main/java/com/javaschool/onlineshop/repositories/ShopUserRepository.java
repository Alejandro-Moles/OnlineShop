package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopUserRepository extends JpaRepository<ShopUser, UUID> {
    Optional<ShopUser> findByMail(String mail);


    boolean existsByMail(String name);
}
