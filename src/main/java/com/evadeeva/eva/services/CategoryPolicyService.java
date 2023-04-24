package com.evadeeva.eva.services;

import com.evadeeva.eva.models.dtos.CategoryPolicyDto;
import com.evadeeva.eva.models.response.CategoryPolicyResponse;

public interface CategoryPolicyService {
	CategoryPolicyDto createCategoryPolicy(long userId, CategoryPolicyDto categoryPolicyDto);

	CategoryPolicyResponse getAllCategoriesPolicyByUserId(long userId, int pageNo, int pageSize, String sortBy, String sortDir);
	
	CategoryPolicyResponse getLockCategoriesPolicy(int pageNo, int pageSize, String sortBy, String sortDir);
	
	CategoryPolicyDto getCategoryPolicyById(long id);
	
	CategoryPolicyDto updateCategoryPolicy(CategoryPolicyDto categoryPolicyDto, long id);
	
	void lockCategoryPolicyById(long id);
	
	void unlockCategoryPolicyByStatusAndId(int satatus, long id);
}
