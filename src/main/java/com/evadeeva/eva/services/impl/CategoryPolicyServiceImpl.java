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
import com.evadeeva.eva.models.CategoryPolicy;
import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.dtos.CategoryPolicyDto;
import com.evadeeva.eva.models.response.CategoryPolicyResponse;
import com.evadeeva.eva.repositories.CategoryPolicyRepository;
import com.evadeeva.eva.repositories.UserRepository;
import com.evadeeva.eva.services.CategoryPolicyService;

@Service
public class CategoryPolicyServiceImpl implements CategoryPolicyService {

	@Autowired
	private CategoryPolicyRepository categoryPolicyRepository;
	private UserRepository userRepository;

	private ModelMapper mapper;

	CategoryPolicyServiceImpl(CategoryPolicyRepository categoryPolicyRepository, ModelMapper mapper,
			UserRepository userRepository) {
		this.categoryPolicyRepository = categoryPolicyRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;

	}

	// Create
	@Override
	public CategoryPolicyDto createCategoryPolicy(long userId, CategoryPolicyDto categoryPolicyDto) {
		// TODO Auto-generated method stub
		// chuyển DTO thành Entity
		CategoryPolicy categoryPolicy = mapToEntity(categoryPolicyDto);

		// Lấy user theo id
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		// đặt user thành category entity
		categoryPolicy.setUser(user);
		// đặt trạng thái mặc định là 1(hiện)
		categoryPolicy.setStatus(1);

		Date currentDateTime = new Date();
		categoryPolicy.setCreatedDate(currentDateTime);

		CategoryPolicy newCategoryPolicy = categoryPolicyRepository.save(categoryPolicy);
		// chuyển Entity thành DTO
		CategoryPolicyDto categoryPolicyResponse = mapToDTO(newCategoryPolicy);

		return categoryPolicyResponse;
	}

	// GetAll
	@Override
	public CategoryPolicyResponse getAllCategoriesPolicyByUserId(long userId, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// tìm tất cả
		Page<CategoryPolicy> categoriesPolicy = categoryPolicyRepository.findByUserId(userId, pageable);
		// lấy nội dung cho đối tượng trang
		List<CategoryPolicy> listOfCategoriesPolicy = categoriesPolicy.getContent();

		List<CategoryPolicyDto> content = listOfCategoriesPolicy.stream()
				.map(categoryPolicy -> mapToDTO(categoryPolicy)).collect(Collectors.toList());

		CategoryPolicyResponse categoryPolicyResponse = new CategoryPolicyResponse();

		categoryPolicyResponse.setContent(content);
		categoryPolicyResponse.setPageNo(categoriesPolicy.getNumber());
		categoryPolicyResponse.setPageSize(categoriesPolicy.getSize());
		categoryPolicyResponse.setTotalElements(categoriesPolicy.getTotalElements());
		categoryPolicyResponse.setTotalPages(categoriesPolicy.getTotalPages());
		categoryPolicyResponse.setLast(categoriesPolicy.isLast());

		return categoryPolicyResponse;

	}

	// Find
	@Override
	public CategoryPolicyDto getCategoryPolicyById(long id) {
		// TODO Auto-generated method stub
		// Lấy categoryPolicy theo id
		CategoryPolicy categoryPolicy = categoryPolicyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoryPolicy", "id", id));
		return mapToDTO(categoryPolicy);
	}

	// Update
	@Override
	public CategoryPolicyDto updateCategoryPolicy(CategoryPolicyDto categoryPolicyDto, long id) {
		// TODO Auto-generated method stub
		// Lấy categoryPolicy theo id
		CategoryPolicy categoryPolicy = categoryPolicyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoryPolicy", "id", id));

		categoryPolicy.setName(categoryPolicyDto.getName());
		
		Date currentDateTime = new Date();
		categoryPolicy.setModifiedDate(currentDateTime);

		CategoryPolicy updatedCategoryPolicy = categoryPolicyRepository.save(categoryPolicy);

		return mapToDTO(updatedCategoryPolicy);
	}

	// lock category
	@Override
	public void lockCategoryPolicyById(long id) {
		// TODO Auto-generated method stub
		// Lấy categoryPolicy theo id
		CategoryPolicy categoryPolicy = categoryPolicyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CategoryPolicy", "id", id));

		// thay đổi trạng thái
		categoryPolicy.setStatus(0);

		// cập nhật thời gian sửa
		Date currentDateTime = new Date();
		categoryPolicy.setModifiedDate(currentDateTime);

		// Lưu thay đổi vào cơ sở dữ liệu
		categoryPolicyRepository.save(categoryPolicy);

	}

	// lấy list lock categoryPolicy
	@Override
	public CategoryPolicyResponse getLockCategoriesPolicy(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// tìm tất cả
		Page<CategoryPolicy> categoriesPolicy = categoryPolicyRepository.findByStatus(0, pageable);
		// lấy nội dung cho đối tượng trang
		List<CategoryPolicy> listOfCategoriesPolicy = categoriesPolicy.getContent();

		List<CategoryPolicyDto> content = listOfCategoriesPolicy.stream()
				.map(categoryPolicy -> mapToDTO(categoryPolicy)).collect(Collectors.toList());

		CategoryPolicyResponse categoryPolicyResponse = new CategoryPolicyResponse();

		categoryPolicyResponse.setContent(content);
		categoryPolicyResponse.setPageNo(categoriesPolicy.getNumber());
		categoryPolicyResponse.setPageSize(categoriesPolicy.getSize());
		categoryPolicyResponse.setTotalElements(categoriesPolicy.getTotalElements());
		categoryPolicyResponse.setTotalPages(categoriesPolicy.getTotalPages());
		categoryPolicyResponse.setLast(categoriesPolicy.isLast());

		return categoryPolicyResponse;
	}

	// unlock categorypolicy
	@Override
	public void unlockCategoryPolicyByStatusAndId(int status, long id) {
		// TODO Auto-generated method stub
		// tìm categrorypolicy theo trạng thái và id
		CategoryPolicy categoryPolicy = categoryPolicyRepository.findByStatusAndId(0, id);
		if (categoryPolicy == null) {
			throw new ResourceNotFoundException("CategoryPolicy", "id", id);
		}
		// thay đổi trạng thái
		categoryPolicy.setStatus(1);
		// cập nhật thời gian sửa
		Date currentDateTime = new Date();
		categoryPolicy.setModifiedDate(currentDateTime);
		categoryPolicyRepository.save(categoryPolicy);

	}

	// chuyển Entity thành DTO
	private CategoryPolicyDto mapToDTO(CategoryPolicy categoryPolicy) {
		CategoryPolicyDto categoryPolicyDto = mapper.map(categoryPolicy, CategoryPolicyDto.class);

		return categoryPolicyDto;
	}

	// chuyển DTO thành Entity
	private CategoryPolicy mapToEntity(CategoryPolicyDto categoryPolicyDto) {
		CategoryPolicy categoryPolicy = mapper.map(categoryPolicyDto, CategoryPolicy.class);

		return categoryPolicy;
	}

}
