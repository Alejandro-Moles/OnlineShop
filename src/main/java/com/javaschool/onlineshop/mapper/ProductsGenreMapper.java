package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.ProductsGenreRequestDTO;
import com.javaschool.onlineshop.models.ProductsGenreModel;
import org.springframework.stereotype.Service;

@Service
public class ProductsGenreMapper {
    public ProductsGenreRequestDTO createGenreProductsDTO(ProductsGenreModel productsGenre){
        ProductsGenreRequestDTO productsGenreDTO= new ProductsGenreRequestDTO();
        productsGenreDTO.setUuid(productsGenre.getProductsGenreUuid());
        productsGenreDTO.setGenre_type(productsGenre.getGenre().getType());
        productsGenreDTO.setProduct_title(productsGenre.getProduct().getTitle());
        productsGenreDTO.setIsDeleted(productsGenre.getIsDeleted());

        return productsGenreDTO;
    }
}
