package com.evadeeva.eva.services;

import java.util.List;

import com.evadeeva.eva.models.dtos.CategoryPolicyDto;
import com.evadeeva.eva.models.response.CategoryPolicyResponse;

public interface CategoryPolicyService {
	CategoryPolicyDto createCategoryPolicy(CategoryPolicyDto categoryPolicyDto);

	CategoryPolicyResponse getAllCategoriesPolicy(int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<CategoryPolicyDto> getLockCategoriesPolicy();
	
	CategoryPolicyDto getCategoryPolicyById(long id);
	
	CategoryPolicyDto updateCategoryPolicy(CategoryPolicyDto categoryPolicyDto, long id);
	
	void lockCategoryPolicyById(long id);
	
	void unlockCategoryPolicyByStatusAndId(int satatus, long id);
}
