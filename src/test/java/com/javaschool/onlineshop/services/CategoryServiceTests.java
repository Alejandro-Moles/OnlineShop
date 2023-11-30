package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CategoryMapper;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTests {

    private final CategoryRepository categoryRepositoryMock = Mockito.mock(CategoryRepository.class);
    private final CategoryMapper categoryMapperMock = Mockito.mock(CategoryMapper.class);
    private final CategoryService categoryService = new CategoryService(categoryRepositoryMock, categoryMapperMock);

    @Test
    void saveCategory_WhenCategoryDoesNotExist_ShouldSaveCategory() {
        when(categoryMapperMock.createCategoryDTO(any())).thenReturn(new CategoryRequestDTO());
        when(categoryRepositoryMock.existsByType(any())).thenReturn(false);

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setType("New Category");
        categoryDTO.setUuid(UUID.randomUUID());
        categoryDTO.setIsDeleted(false);
        CategoryRequestDTO savedCategoryDTO = categoryService.saveCategory(categoryDTO);

        assertNotNull(savedCategoryDTO);
    }

    @Test
    void saveCategory_WhenCategoryExists_ShouldThrowResourceDuplicateException() {
        when(categoryRepositoryMock.existsByType(any())).thenReturn(true);

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setType("Existing Category");

        assertThrows(ResourceDuplicate.class, () -> categoryService.saveCategory(categoryDTO));
    }
}