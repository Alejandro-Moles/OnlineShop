package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequestDTO categoryDTO) {
        CategoryRequestDTO result = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok("Category created : " + result.getType());
    }
}
