package com.evadeeva.eva.service;

import java.util.List;

import com.evadeeva.eva.models.dtos.CategoryDto;
import com.evadeeva.eva.models.response.CategoryResponse;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<CategoryDto> getLockCategories();
	
	CategoryDto getCategoryById(long id);
	
	CategoryDto updateCategory(CategoryDto categoryDto, long id);
	
	void lockCategoryById(long id);
	
	void unlockCategoryByStatusAndId(int satatus, long id);
}
