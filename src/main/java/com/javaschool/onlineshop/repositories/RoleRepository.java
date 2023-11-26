package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByType(String type);
    boolean existsByType(String name);
    List<RoleModel> findByTypeIn(List<String> types);
}
