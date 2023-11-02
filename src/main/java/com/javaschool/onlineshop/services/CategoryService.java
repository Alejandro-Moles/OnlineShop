package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CategoryMapper;
import com.javaschool.onlineshop.models.Category;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;


	public CategoryRequestDTO saveCategory(CategoryRequestDTO categoryDTO) {
		Category category = createCategoryEntity(categoryDTO, new Category());
		if (categoryRepository.existsByType(category.getType())) {
			throw new ResourceDuplicate("Category already exists");
		}
		categoryRepository.save(category);
		return createCategoryDTO(category);
	}

	@Transactional(readOnly = true)
	public Category getCategoryById(UUID id) {
		return categoryRepository.findById(id).orElseThrow(() -> new NoExistData("This category don't exist"));
	}

	public void deleteCategory(UUID id) {
		categoryRepository.deleteById(id);
	}

	private CategoryRequestDTO createCategoryDTO(Category category){
		return categoryMapper.createCategoryDTO(category);
	}

	private Category createCategoryEntity(CategoryRequestDTO categoryDTO, Category category){
		category.setType(categoryDTO.getType());
		category.setIsDeleted(categoryDTO.getIsDeleted());
		return category;
	}

	@Transactional(readOnly = true)
	public List<CategoryRequestDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(this::createCategoryDTO).toList();
	}

	public void updateCategory(UUID uuid, CategoryRequestDTO categoryDTO){
		Category category = loadCategory(uuid);
		createCategoryEntity(categoryDTO, category);
		categoryRepository.save(category);
	}

	@Transactional(readOnly = true)
	private Category loadCategory(UUID uuid){
		return categoryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Category don't exist"));
	}

}
