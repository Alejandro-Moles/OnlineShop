package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.models.CategoryModel;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryRequestDTO createCategoryDTO(CategoryModel category){
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setUuid(category.getCategoryUuid());
        categoryDTO.setType(category.getType());
        categoryDTO.setIsDeleted(category.getIsDeleted());
        return categoryDTO;
    }
}
