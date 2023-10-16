package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.ProductRequestDTO;
import com.javaschool.onlineshop.Models.Products;
import org.springframework.stereotype.Service;

@Service
public class ProductsMapper {
    public ProductRequestDTO createProductDTO(Products products){
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setCategory(products.getCategory().getType());
        productDTO.setPlatform(products.getPlatform().getType());
        productDTO.setTitle(products.getTitle());
        productDTO.setPrice(products.getPrice());
        productDTO.setWeight(products.getWeight());
        productDTO.setStock(products.getStock());
        productDTO.setPegi(products.getPEGI());
        productDTO.setIsDigital(products.getIsDigital());
        productDTO.setDescription(products.getDescription());
        productDTO.setImage(products.getImage());
        productDTO.setIsDeleted(products.getIsDeleted());

        return productDTO;
    }
}
