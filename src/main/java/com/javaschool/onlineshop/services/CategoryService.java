package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CategoryMapper;
import com.javaschool.onlineshop.models.Category;
import com.javaschool.onlineshop.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;


	public CategoryRequestDTO saveCategory(CategoryRequestDTO categoryDTO) {
		Category category = new Category();
		category.setType(categoryDTO.getType());
		category.setIsDeleted(false);

		if (categoryRepository.existsByType(category.getType())) {
			throw new ResourceDuplicate("Category already exists");
		}

		categoryRepository.save(category);
		return createCategoryDTO(category);
	}

	public Category getCategoryPorId(UUID id) {
		return categoryRepository.findById(id).orElseThrow(() -> new NoExistData("This city don't exist"));
	}

	public void deleteCategory(UUID id) {
		categoryRepository.deleteById(id);
	}

	private CategoryRequestDTO createCategoryDTO(Category category){
		return categoryMapper.createCategoryDTO(category);
	}
}
