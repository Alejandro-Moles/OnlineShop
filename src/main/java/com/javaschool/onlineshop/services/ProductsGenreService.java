package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ProductsGenreRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ProductsGenreMapper;
import com.javaschool.onlineshop.models.*;
import com.javaschool.onlineshop.repositories.GenreRepository;
import com.javaschool.onlineshop.repositories.ProductsGenreRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsGenreService {
    private final ProductsGenreRepository productsGenreRepository;
    private final ProductsRepository productsRepository;
    private final GenreRepository genreRepository;
    private final ProductsGenreMapper productsGenreMapper;

    public ProductsGenreRequestDTO saveProductsGenre(ProductsGenreRequestDTO productsGenreDTO) {
        ProductsGenre productsGenre = createProductsGenreEntity(productsGenreDTO, new ProductsGenre());
        if(productsGenreRepository.existsByProductAndGenre(productsGenre.getProduct(), productsGenre.getGenre())){
            throw new ResourceDuplicate("Products already exists with that genre");
        }
        productsGenreRepository.save(productsGenre);
        return createProductsGenreDTO(productsGenre);
    }

    @Transactional(readOnly = true)
    private Products findProducts(String title){
        return productsRepository.findByTitle(title).orElseThrow(() -> new NoExistData("This product don't exist"));
    }

    @Transactional(readOnly = true)
    private Genre findGenre(String type){
        return genreRepository.findByType(type).orElseThrow(() -> new NoExistData("This genre don't exist"));
    }

    private ProductsGenreRequestDTO createProductsGenreDTO(ProductsGenre productsGenre){
        return productsGenreMapper.createGenreProductsDTO(productsGenre);
    }

    private ProductsGenre createProductsGenreEntity(ProductsGenreRequestDTO productsGenreDTO, ProductsGenre productsGenre){
        productsGenre.setGenre(findGenre(productsGenreDTO.getGenre_type()));
        productsGenre.setProduct(findProducts(productsGenreDTO.getProduct_title()));
        productsGenre.setIsDeleted(productsGenreDTO.getIsDeleted());
        return productsGenre;
    }
}
