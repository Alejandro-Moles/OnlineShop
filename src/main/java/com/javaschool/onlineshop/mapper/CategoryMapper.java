package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.models.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryRequestDTO createCategoryDTO(Category category){
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setUuid(category.getCategoryUuid());
        categoryDTO.setType(category.getType());
        categoryDTO.setIsDeleted(category.getIsDeleted());
        return categoryDTO;
    }
}
