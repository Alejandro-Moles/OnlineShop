package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CategoryMapper;
import com.javaschool.onlineshop.models.CategoryModel;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTests {

    private final CategoryRepository categoryRepositoryMock = Mockito.mock(CategoryRepository.class);
    private final CategoryMapper categoryMapperMock = Mockito.mock(CategoryMapper.class);
    private final CategoryService categoryService = new CategoryService(categoryRepositoryMock, categoryMapperMock);

    /**
     * Test for saving a category when the category does not exist, checking if it successfully saves the category.
     */
    @Test
    void saveCategory_WhenCategoryDoesNotExist_ShouldSaveCategory() {
        // Mocking data
        when(categoryMapperMock.createCategoryDTO(any())).thenReturn(new CategoryRequestDTO());
        when(categoryRepositoryMock.existsByType(any())).thenReturn(false);

        // Creating a CategoryDTO
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setType("New Category");
        categoryDTO.setUuid(UUID.randomUUID());
        categoryDTO.setIsDeleted(false);

        // Calling the service method
        CategoryRequestDTO savedCategoryDTO = categoryService.saveCategory(categoryDTO);
        System.out.println(savedCategoryDTO);

        // Assertions
        assertNotNull(savedCategoryDTO);
    }

    /**
     * Test for saving a category when the category exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveCategory_WhenCategoryExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(categoryRepositoryMock.existsByType(any())).thenReturn(true);

        // Creating a CategoryDTO
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setType("Existing Category");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> categoryService.saveCategory(categoryDTO));
    }

    /**
     * Test for updating a category when the category exists, checking if it successfully updates the category.
     */
    @Test
    void updateCategory_WhenCategoryExists_ShouldUpdateCategory() {
        // Mocking data
        UUID existingCategoryUUID = UUID.randomUUID();
        CategoryRequestDTO updatedCategoryDTO = new CategoryRequestDTO();
        updatedCategoryDTO.setType("Updated Category");
        updatedCategoryDTO.setUuid(existingCategoryUUID);
        updatedCategoryDTO.setIsDeleted(false);

        CategoryModel existingCategory = new CategoryModel();
        existingCategory.setCategoryUuid(existingCategoryUUID);
        existingCategory.setType("Existing Category");
        existingCategory.setIsDeleted(false);

        when(categoryRepositoryMock.findById(existingCategoryUUID)).thenReturn(Optional.of(existingCategory));

        // Calling the service method
        categoryService.updateCategory(existingCategoryUUID, updatedCategoryDTO);

        // Verifying repository save method is called
        verify(categoryRepositoryMock).save(any(CategoryModel.class));

        // Assertions
        assertEquals("Updated Category", existingCategory.getType());
    }

    /**
     * Test for loading a category when the category exists, checking if it returns the loaded category.
     */
    @Test
    void loadCategory_WhenCategoryExists_ShouldReturnCategory() {
        // Mocking data
        UUID existingCategoryUUID = UUID.randomUUID();
        CategoryModel existingCategory = new CategoryModel();
        existingCategory.setCategoryUuid(existingCategoryUUID);
        existingCategory.setType("Existing Category");
        existingCategory.setIsDeleted(false);

        when(categoryRepositoryMock.findById(existingCategoryUUID)).thenReturn(Optional.of(existingCategory));

        // Calling the service method
        CategoryModel loadedCategory = categoryService.loadCategory(existingCategoryUUID);

        // Assertions
        assertEquals(existingCategory, loadedCategory);
    }

    /**
     * Test for loading a category when the category does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadCategory_WhenCategoryDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentCategoryUUID = UUID.randomUUID();
        when(categoryRepositoryMock.findById(nonExistentCategoryUUID)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> categoryService.loadCategory(nonExistentCategoryUUID));
    }

}