package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ProductsMapper;
import com.javaschool.onlineshop.models.*;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final CategoryRepository categoryRepository;
    private final PlatformsRepository platformsRepository;

    public ProductRequestDTO saveProduct(ProductRequestDTO productDTO){
        Products products = new Products();
        products.setTitle(productDTO.getTitle());
        products.setPrice(productDTO.getPrice());
        products.setWeight(productDTO.getWeight());
        products.setPEGI(productDTO.getPegi());
        products.setStock(productDTO.getStock());
        products.setIsDigital(productDTO.getIsDigital());
        products.setDescription(productDTO.getDescription());
        products.setImage(productDTO.getImage());
        products.setIsDeleted(productDTO.getIsDeleted());
        products.setCategory(findCategory(productDTO.getCategory()));
        products.setPlatform(findPlatform(productDTO.getPlatform()));

        if (productsRepository.existsByTitle(products.getTitle())) {
            throw new ResourceDuplicate("Product already exists");
        }

        productsRepository.save(products);
        return createProductDTO(products);
    }

    private ProductRequestDTO createProductDTO(Products products){
        return productsMapper.createProductDTO(products);
    }

    @Transactional(readOnly = true)
    private Category findCategory(String type){
        return categoryRepository.findByType(type).orElseThrow(() -> new NoExistData("This category don't exist"));
    }

    @Transactional(readOnly = true)
    private Platforms findPlatform(String type){
        return platformsRepository.findByType(type).orElseThrow(() -> new NoExistData("This platform don't exist"));
    }
}
