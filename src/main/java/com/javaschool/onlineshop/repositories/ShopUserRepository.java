package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopUserRepository extends JpaRepository<ShopUserModel, UUID> {
    Optional<ShopUserModel> findByMail(String mail);


    boolean existsByMail(String name);
}
