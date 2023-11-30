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

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	private CategoryRequestDTO createCategoryDTO(CategoryModel category){
		return categoryMapper.createCategoryDTO(category);
	}

	private CategoryModel createCategoryEntity(CategoryRequestDTO categoryDTO, CategoryModel category){
		category.setType(categoryDTO.getType());
		category.setIsDeleted(categoryDTO.getIsDeleted());
		return category;
	}

	@Transactional
	public CategoryRequestDTO saveCategory(CategoryRequestDTO categoryDTO) {
		CategoryModel category = createCategoryEntity(categoryDTO, new CategoryModel());
		if (categoryRepository.existsByType(category.getType())) {
			throw new ResourceDuplicate("Category already exists");
		}
		categoryRepository.save(category);
		return createCategoryDTO(category);
	}

	@Transactional
	public void updateCategory(UUID uuid, CategoryRequestDTO categoryDTO){
		CategoryModel category = loadCategory(uuid);
		createCategoryEntity(categoryDTO, category);
		categoryRepository.save(category);
	}

	@Transactional(readOnly = true)
	public List<CategoryRequestDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(this::createCategoryDTO).toList();
	}

	@Transactional(readOnly = true)
	private CategoryModel loadCategory(UUID uuid){
		return categoryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Category don't exist"));
	}

	@Transactional(readOnly = true)
	public List<CategoryRequestDTO> getAllAvailableCategories(){
		return categoryRepository.findAllByIsDeletedFalse().stream().map(this::createCategoryDTO).toList();
	}
}
