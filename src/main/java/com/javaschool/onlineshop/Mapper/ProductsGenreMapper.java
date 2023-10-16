package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.ProductsGenreRequestDTO;
import com.javaschool.onlineshop.DTO.GenreRequestDTO;
import com.javaschool.onlineshop.Models.ProductsGenre;
import org.springframework.stereotype.Service;

@Service
public class ProductsGenreMapper {
    public ProductsGenreRequestDTO createGenreProductsDTO(ProductsGenre productsGenre){
        ProductsGenreRequestDTO productsGenreDTO= new ProductsGenreRequestDTO();
        productsGenreDTO.setGenre_type(productsGenre.getGenre().getType());
        productsGenreDTO.setProduct_title(productsGenre.getProduct().getTitle());
        productsGenreDTO.setIsDeleted(productsGenre.getIsDeleted());

        return productsGenreDTO;
    }
}
