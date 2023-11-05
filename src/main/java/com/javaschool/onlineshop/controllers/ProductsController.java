package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<ProductRequestDTO>> getAllProducts(){
        List<ProductRequestDTO> result = productsService.getAllProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductRequestDTO> getProductsbyUuid(@PathVariable UUID uuid){
        ProductRequestDTO result = productsService.getProductsbyUuid(uuid);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateProduct(@PathVariable UUID uuid, @RequestBody ProductRequestDTO productDTO){
        productsService.updateProduct(uuid, productDTO);
        return ResponseEntity.ok("Product changed succesfully");
    }


}
