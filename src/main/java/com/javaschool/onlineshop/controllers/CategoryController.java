package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    // Endpoint to create a new category
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequestDTO categoryDTO) {
        CategoryRequestDTO result = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok("Category created: " + result.getType());
    }

    // Endpoint to get all categories
    @GetMapping
    public ResponseEntity<List<CategoryRequestDTO>> getAllCategories() {
        List<CategoryRequestDTO> result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update an existing category by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateCategory(@PathVariable UUID uuid, @RequestBody CategoryRequestDTO categoryDTO) {
        categoryService.updateCategory(uuid, categoryDTO);
        return ResponseEntity.ok("Category changed successfully");
    }

    // Endpoint to get all available categories
    @GetMapping("/availableCategory")
    public ResponseEntity<List<CategoryRequestDTO>> getAllAvailableCategories() {
        List<CategoryRequestDTO> result = categoryService.getAllAvailableCategories();
        return ResponseEntity.ok(result);
    }
}
