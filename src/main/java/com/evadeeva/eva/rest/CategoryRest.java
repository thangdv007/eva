package com.evadeeva.eva.rest;

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

import com.evadeeva.eva.models.dtos.CategoryDto;
import com.evadeeva.eva.models.response.CategoryResponse;
import com.evadeeva.eva.services.CategoryService;
import com.evadeeva.eva.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user/category")
public class CategoryRest {
	private CategoryService categoryService;

	public CategoryRest(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// create category
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create/{id}")
	public ResponseEntity<CategoryDto> createCategory(@Valid @PathVariable(name = "id") long userId,
			@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(userId, categoryDto), HttpStatus.CREATED);
	}

	// get all category by user id
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public CategoryResponse getCategoryByUserId(
			@PathVariable(name ="id") long userId,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return categoryService.getCategoryByUserId(userId, pageNo, pageSize, sortBy, sortDir);
	}

//	// get all category
//	@PreAuthorize("hasRole('ADMIN')")
//	@GetMapping
//	public CategoryResponse getAllCategories(
//			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
//			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
//			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
//		return categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir);
//	}

	// get category by id
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/categoryID/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	// update category
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable(name = "id") long id) {
		CategoryDto categoryResponse = categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockCategory(@PathVariable(name = "id") long id) {
		categoryService.lockCategoryById(id);
		return new ResponseEntity<>("Category entity delete successfully.", HttpStatus.OK);
	}

	// get all lock category
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lock")
	public CategoryResponse getLockCategories(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return categoryService.getLockCategories(pageNo, pageSize, sortBy, sortDir);
	}

	// unlock
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<String> unlockCategory(@PathVariable(name = "id") long id) {
		categoryService.unlockCategoryByStatusAndId(1, id);
		return new ResponseEntity<>("Category entity unlock successfully.", HttpStatus.OK);
	}

}
