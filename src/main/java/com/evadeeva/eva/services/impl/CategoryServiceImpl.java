package com.evadeeva.eva.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evadeeva.eva.exceptions.ResourceNotFoundException;
import com.evadeeva.eva.models.Category;
import com.evadeeva.eva.models.dtos.CategoryDto;
import com.evadeeva.eva.models.response.CategoryResponse;
import com.evadeeva.eva.repositories.CategoryRepository;
import com.evadeeva.eva.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	private ModelMapper mapper;

	CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
		this.categoryRepository = categoryRepository;
        this.mapper = mapper;

	}

	// Create
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		// chuyển DTO thành Entity
		Category category = mapToEntity(categoryDto);
		category.setStatus(1);

		Date currentDateTime = new Date();
		category.setCreatedDate(currentDateTime);

		Category newCategory = categoryRepository.save(category);
		// chuyển Entity thành DTO
		CategoryDto categoryResponse = mapToDTO(newCategory);

		return categoryResponse;
	}

	// GetAll
	@Override
	public CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		//tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		//tìm tất cả
		Page<Category> categories = categoryRepository.findAll(pageable);
		//lấy nội dung cho đối tượng trang
		List<Category> listOfCategories = categories.getContent();
		
		List<CategoryDto> content = listOfCategories.stream().map(category -> mapToDTO(category)).collect(Collectors.toList());
	
		CategoryResponse categoryResponse = new CategoryResponse();
		
		categoryResponse.setContent(content);
		categoryResponse.setPageNo(categories.getNumber());
		categoryResponse.setPageSize(categories.getSize());
		categoryResponse.setTotalElements(categories.getTotalElements());
		categoryResponse.setTotalPages(categories.getTotalPages());
		categoryResponse.setLast(categories.isLast());
		
		return categoryResponse;
		
	}

	// Find
	@Override
	public CategoryDto getCategoryById(long id) {
		// TODO Auto-generated method stub
		// Lấy category theo id
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return mapToDTO(category);
	}

	// Update
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
		// TODO Auto-generated method stub
		// Lấy category theo id
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());

		Date currentDateTime = new Date();
		category.setModifiedDate(currentDateTime);

		Category updatedCategory = categoryRepository.save(category);

		return mapToDTO(updatedCategory);
	}

	// lock category
	@Override
	public void lockCategoryById(long id) {
		// TODO Auto-generated method stub
		// Lấy category theo id
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		// thay đổi trạng thái
		category.setStatus(0);

		// cập nhật thời gian sửa
		Date currentDateTime = new Date();
		category.setModifiedDate(currentDateTime);

		// Lưu thay đổi vào cơ sở dữ liệu
		categoryRepository.save(category);

	}

	// lấy list lock category
	@Override
	public List<CategoryDto> getLockCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findByStatus(0);
		return categories.stream().map(category -> mapToDTO(category)).collect(Collectors.toList());
	}

	// unlock category
	@Override
	public void unlockCategoryByStatusAndId(int status, long id) {
		// TODO Auto-generated method stub
		//tìm categrory theo trạng thái và id
		Category category = categoryRepository.findByStatusAndId(0, id);
		if (category == null) {
			throw new ResourceNotFoundException("Category", "id", id);
		}
		//thay đổi trạng thái
		category.setStatus(1);
		// cập nhật thời gian sửa
		Date currentDateTime = new Date();
		category.setModifiedDate(currentDateTime);
		categoryRepository.save(category);

	}

	// chuyển Entity thành DTO
	private CategoryDto mapToDTO(Category category) {
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		
//		CategoryDto categoryDto = new CategoryDto();
//		categoryDto.setId(category.getId());
//		categoryDto.setTitle(category.getTitle());
//		categoryDto.setDescription(category.getDescription());
//		categoryDto.setCreatedDate(category.getCreatedDate());
//		categoryDto.setModifiedDate(category.getModifiedDate());
//		categoryDto.setStatus(category.getStatus());
		return categoryDto;
	}

	// chuyển DTO thành Entity
	private Category mapToEntity(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		
//		Category category = new Category();
//		category.setId(categoryDto.getId());
//		category.setTitle(categoryDto.getTitle());
//		category.setDescription(categoryDto.getDescription());
//		category.setCreatedDate(categoryDto.getCreatedDate());
//		category.setModifiedDate(categoryDto.getModifiedDate());
//		category.setStatus(categoryDto.getStatus());
		return category;
	}

}
