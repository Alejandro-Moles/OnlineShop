package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.PlatformsRequestDTO;
import com.javaschool.onlineshop.DTO.ProductRequestDTO;
import com.javaschool.onlineshop.Services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @PostMapping
    public ResponseEntity<String> createProducts(@RequestBody ProductRequestDTO productDTO) {
        ProductRequestDTO result = productsService.saveProduct(productDTO);
        return ResponseEntity.ok("Product created : " + result.getTitle());
    }
}
