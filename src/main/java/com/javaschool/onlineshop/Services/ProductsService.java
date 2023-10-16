package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.ProductRequestDTO;
import com.javaschool.onlineshop.Mapper.ProductsMapper;
import com.javaschool.onlineshop.Models.*;
import com.javaschool.onlineshop.Repositories.CategoryRepository;
import com.javaschool.onlineshop.Repositories.PlatformsRepository;
import com.javaschool.onlineshop.Repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        productsRepository.save(products);
        return createProductDTO(products);
    }

    private ProductRequestDTO createProductDTO(Products products){
        return productsMapper.createProductDTO(products);
    }

    private Category findCategory(String type){
        return categoryRepository.findByType(type).orElseThrow(null);
    }

    private Platforms findPlatform(String type){
        return platformsRepository.findByType(type).orElseThrow(null);
    }
}
