package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.CategoryRequestDTO;
import com.javaschool.onlineshop.Models.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryRequestDTO createCategoryDTO(Category category){
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setType(category.getType());
        categoryDTO.setDeleted(category.getIsDeleted());

        return categoryDTO;
    }
}
