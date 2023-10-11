package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.CategoryRequestDTO;
import com.javaschool.onlineshop.Models.Category;
import com.javaschool.onlineshop.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequestDTO categoryDTO) {
        Category category = new Category();
        category.setType(categoryDTO.getType());
        category.setIsDeleted(false);

        categoryService.saveCategory(category);

        return ResponseEntity.ok("Category created with ID: " + category.getCategory_uuid());
    }
}
