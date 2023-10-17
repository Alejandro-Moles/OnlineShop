package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface PostalCodeRepository extends JpaRepository<PostalCode, UUID> {
    Optional<PostalCode> findByContent(String content);
    boolean existsByContent(String content);
}
