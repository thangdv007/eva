package com.evadeeva.eva.mapper;

import org.mapstruct.Mapper;

import com.evadeeva.eva.models.Category;
import com.evadeeva.eva.models.request.CategoryRequest;
import com.evadeeva.eva.models.response.CategoryResponse;
@Mapper
public interface CategoryMapper {
	Category mapCategoryToModel(CategoryRequest categoryRequested);
	
	CategoryResponse mapModelToResponse(Category category);
}
