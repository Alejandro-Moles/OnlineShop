package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.Models.Category;
import com.javaschool.onlineshop.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	public Category getCategoryPorId(UUID id) {
		return categoryRepository.findById(id).orElse(null);
	}

	public void deleteCategory(UUID id) {
		categoryRepository.deleteById(id);
	}
}
