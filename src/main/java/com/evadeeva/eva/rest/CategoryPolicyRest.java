package com.evadeeva.eva.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evadeeva.eva.models.dtos.CategoryPolicyDto;
import com.evadeeva.eva.models.response.CategoryPolicyResponse;
import com.evadeeva.eva.services.CategoryPolicyService;
import com.evadeeva.eva.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorypolicy")
public class CategoryPolicyRest {
	private CategoryPolicyService categoryPolicyService;

	public CategoryPolicyRest(CategoryPolicyService categoryPolicyService) {
		this.categoryPolicyService = categoryPolicyService;
	}

	// create category policy
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<CategoryPolicyDto> createCategoryPolicy(@Valid @RequestBody CategoryPolicyDto categoryPolicyDto) {
		return new ResponseEntity<>(categoryPolicyService.createCategoryPolicy(categoryPolicyDto), HttpStatus.CREATED);
	}

	// get all category policy
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public CategoryPolicyResponse getAllCategories(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir
		){
		return categoryPolicyService.getAllCategoriesPolicy(pageNo, pageSize, sortBy, sortDir);
	}

	// get category policy by id
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryPolicyDto> getCategoryPolicyById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(categoryPolicyService.getCategoryPolicyById(id));
	}

	// update category policy
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryPolicyDto> updateCategoryPolicy(@Valid @RequestBody CategoryPolicyDto categoryPolicyDto,
			@PathVariable(name = "id") long id) {
		CategoryPolicyDto categoryPolicyResponse = categoryPolicyService.updateCategoryPolicy(categoryPolicyDto, id);
		return new ResponseEntity<>(categoryPolicyResponse, HttpStatus.OK);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockCategoryPolicy(@PathVariable(name = "id") long id) {
		categoryPolicyService.lockCategoryPolicyById(id);
		return new ResponseEntity<>("CategoryPolicy entity delete successfully.", HttpStatus.OK);
	}

	// get all lock category policy
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lock")
	public List<CategoryPolicyDto> getLockCategoriesPolicy() {
		return categoryPolicyService.getLockCategoriesPolicy();
	}

	// unlock
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<String> unlockCategoryPolicy(@PathVariable(name = "id") long id) {
		categoryPolicyService.unlockCategoryPolicyByStatusAndId(1, id);
		return new ResponseEntity<>("CategoryPolicy entity unlock successfully.", HttpStatus.OK);
	}

}
