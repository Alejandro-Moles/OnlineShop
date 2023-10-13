package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.CategoryRequestDTO;
import com.javaschool.onlineshop.Mapper.CategoryMapper;
import com.javaschool.onlineshop.Models.Category;
import com.javaschool.onlineshop.Repositories.CategoryRepository;
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

		categoryRepository.save(category);
		return createCategoryDTO(category);
	}

	public Category getCategoryPorId(UUID id) {
		return categoryRepository.findById(id).orElse(null);
	}

	public void deleteCategory(UUID id) {
		categoryRepository.deleteById(id);
	}

	private CategoryRequestDTO createCategoryDTO(Category category){
		return categoryMapper.createCategoryDTO(category);
	}
}
