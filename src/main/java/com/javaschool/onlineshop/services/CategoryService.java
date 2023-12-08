package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CategoryMapper;
import com.javaschool.onlineshop.models.CategoryModel;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

	// Injecting the CategoryRepository for database operations
	private final CategoryRepository categoryRepository;

	// Injecting the CategoryMapper for mapping between DTO and entity
	private final CategoryMapper categoryMapper;

	// This method creates a CategoryDTO from a CategoryModel
	private CategoryRequestDTO createCategoryDTO(CategoryModel category) {
		return categoryMapper.createCategoryDTO(category);
	}

	// This method updates a CategoryModel entity with data from a CategoryDTO
	private CategoryModel createCategoryEntity(CategoryRequestDTO categoryDTO, CategoryModel category) {
		category.setType(categoryDTO.getType());
		category.setIsDeleted(categoryDTO.getIsDeleted());
		return category;
	}

	// This method saves a new category to the database
	@Transactional
	public CategoryRequestDTO saveCategory(CategoryRequestDTO categoryDTO) {
		// Creating a CategoryModel entity from the DTO
		CategoryModel category = createCategoryEntity(categoryDTO, new CategoryModel());

		// Checking if a category with the same type already exists
		if (categoryRepository.existsByType(category.getType())) {
			throw new ResourceDuplicate("Category already exists");
		}

		// Saving the category to the database
		categoryRepository.save(category);

		// Returning the saved category as a DTO
		return createCategoryDTO(category);
	}

	// This method updates an existing category in the database
	@Transactional
	public void updateCategory(UUID uuid, CategoryRequestDTO categoryDTO) {
		CategoryModel category = loadCategory(uuid);
		if (categoryRepository.existsByType(categoryDTO.getType())) {
			throw new ResourceDuplicate("This category already exists in the database");
		}
		//Updating the category entity with data from the DTO
		createCategoryEntity(categoryDTO, category);
		//Saving the updated category to the database
		categoryRepository.save(category);
	}

	// This method retrieves all categories from the database
	@Transactional(readOnly = true)
	public List<CategoryRequestDTO> getAllCategories() {
		return categoryRepository.findAll().stream().map(this::createCategoryDTO).toList();
	}

	// This method loads a category by its UUID from the database
	@Transactional(readOnly = true)
	public CategoryModel loadCategory(UUID uuid) {
		return categoryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Category doesn't exist"));
	}

	// This method retrieves all available (non-deleted) categories from the database
	@Transactional(readOnly = true)
	public List<CategoryRequestDTO> getAllAvailableCategories() {
		return categoryRepository.findAllByIsDeletedFalse().stream().map(this::createCategoryDTO).toList();
	}
}
