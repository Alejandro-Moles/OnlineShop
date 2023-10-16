package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.CityRequestDTO;
import com.javaschool.onlineshop.DTO.PlatformsRequestDTO;
import com.javaschool.onlineshop.DTO.ProductsGenreRequestDTO;
import com.javaschool.onlineshop.Exception.ResourceDuplicate;
import com.javaschool.onlineshop.Mapper.ProductsGenreMapper;
import com.javaschool.onlineshop.Models.*;
import com.javaschool.onlineshop.Repositories.GenreRepository;
import com.javaschool.onlineshop.Repositories.ProductsGenreRepository;
import com.javaschool.onlineshop.Repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsGenreService {
    private final ProductsGenreRepository productsGenreRepository;
    private final ProductsRepository productsRepository;
    private final GenreRepository genreRepository;
    private final ProductsGenreMapper productsGenreMapper;

    public ProductsGenreRequestDTO saveProductsGenre(ProductsGenreRequestDTO productsGenreDTO) {
        ProductsGenre productsGenre = new ProductsGenre();
        productsGenre.setGenre(findGenre(productsGenreDTO.getGenre_type()));
        productsGenre.setProduct(findProducts(productsGenreDTO.getProduct_title()));
        productsGenre.setIsDeleted(productsGenreDTO.getIsDeleted());

        if(productsGenreRepository.existsByProductAndGenre(productsGenre.getProduct(), productsGenre.getGenre())){
            throw new ResourceDuplicate("Products already exists with that genre");
        }
        productsGenreRepository.save(productsGenre);
        return createProductsGenreDTO(productsGenre);
    }

    private Products findProducts(String title){
        return productsRepository.findByTitle(title).orElseThrow(null);
    }

    private Genre findGenre(String type){
        return genreRepository.findByType(type).orElseThrow(null);
    }

    private ProductsGenreRequestDTO createProductsGenreDTO(ProductsGenre productsGenre){
        return productsGenreMapper.createGenreProductsDTO(productsGenre);
    }
}
