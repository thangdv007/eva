package com.evadeeva.eva.services;

import com.evadeeva.eva.models.dtos.CategoryDto;
import com.evadeeva.eva.models.response.CategoryResponse;

public interface CategoryService {
	CategoryDto createCategory(long userId, CategoryDto categoryDto);
	
	CategoryResponse getCategoryByUserId(long userId, int pageNo, int pageSize, String sortBy, String sortDir);
	
//	CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
	
	CategoryResponse getLockCategories(int pageNo, int pageSize, String sortBy, String sortDir);
	
	CategoryDto getCategoryById(long id);
	
	CategoryDto updateCategory(CategoryDto categoryDto, long id);
	
	void lockCategoryById(long id);
	
	void unlockCategoryByStatusAndId(int satatus, long id);
}
