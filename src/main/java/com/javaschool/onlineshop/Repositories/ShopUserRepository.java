package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, UUID> {
}
