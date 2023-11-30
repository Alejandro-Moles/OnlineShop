package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.TotalSaleProductDTO;
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

    @PutMapping("deleteProduct/{uuid}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID uuid){
        productsService.deleteProduct(uuid);
        return ResponseEntity.ok("Product changed succesfully");
    }

    @PutMapping("updateProduct/{uuid}")
    public ResponseEntity<String> updateProduct(@PathVariable UUID uuid, @RequestBody ProductRequestDTO productDTO){
        productsService.updateProduct(uuid, productDTO);
        return ResponseEntity.ok("Product changed succesfully");
    }

    @GetMapping("/topProducts")
    public ResponseEntity<List<TotalSaleProductDTO>> getTopProducts(){
        List<TotalSaleProductDTO> result = productsService.getTopSaleProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/availableProducts")
    public ResponseEntity<List<ProductRequestDTO>> getAvailableProducts() {
        List<ProductRequestDTO> result = productsService.getAvailableProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/availableTopProducts")
    public ResponseEntity<List<ProductRequestDTO>> getAvailableTopProducts() {
        List<ProductRequestDTO> result = productsService.getAvailableTopProducts();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/assignGenres/{uuid}")
    public ResponseEntity<String> assignRolesToUser(
            @PathVariable UUID uuid,
            @RequestBody List<String> genresTypes
    ) {
        productsService.assignGenresToProducts(uuid, genresTypes);
        return ResponseEntity.ok("Roles assigned successfully to user with UUID: " + uuid);
    }


}
