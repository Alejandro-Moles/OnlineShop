package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.ProductsGenreRequestDTO;
import com.javaschool.onlineshop.services.ProductsGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productsgenre")
@RequiredArgsConstructor
public class ProductsGenreController {
    private final ProductsGenreService productsGenreService;

    @PostMapping
    public ResponseEntity<String> createProductsGenre(@RequestBody ProductsGenreRequestDTO productsGenreDTO) {
        ProductsGenreRequestDTO result = productsGenreService.saveProductsGenre(productsGenreDTO);
        return ResponseEntity.ok("Product_Genre created : " + result.getProduct_title());
    }
}
