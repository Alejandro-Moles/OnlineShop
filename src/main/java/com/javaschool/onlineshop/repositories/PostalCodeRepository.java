package com.javaschool.onlineshop.repositories;

import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.models.PostalCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostalCodeRepository extends JpaRepository<PostalCodeModel, UUID> {
    Optional<PostalCodeModel> findByContent(String content);
    boolean existsByContent(String content);

    List<PostalCodeModel> findAllByIsDeletedFalse();
}
