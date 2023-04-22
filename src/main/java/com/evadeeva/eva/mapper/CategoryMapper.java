package com.evadeeva.eva.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.evadeeva.eva.models.Category;
import com.evadeeva.eva.models.dtos.CategoryDto;
import com.evadeeva.eva.models.requested.CategoryRequest;
@Mapper
public interface CategoryMapper {

		// mapper one model to dto
		CategoryDto mapModelToDTO(Category category);

		// mapper list model to dto
		List<CategoryDto> mapModelToDTOs(List<Category> categories);

		// mapper one dto to model
		Category mapDTOToModel(CategoryDto categoryDto);

		// mapper list dto to model
		List<Category> mapDTOToModels(List<CategoryDto> categoryDtos);

		@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
		void updateModel(@MappingTarget Category category, CategoryRequest categoryRequest);

}
