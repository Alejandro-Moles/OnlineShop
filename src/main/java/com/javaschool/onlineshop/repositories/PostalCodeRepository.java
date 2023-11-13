package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PostalCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface PostalCodeRepository extends JpaRepository<PostalCodeModel, UUID> {
    Optional<PostalCodeModel> findByContent(String content);
    boolean existsByContent(String content);
}
