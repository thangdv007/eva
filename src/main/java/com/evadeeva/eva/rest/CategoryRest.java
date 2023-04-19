package com.evadeeva.eva.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.evadeeva.eva.service.CategoryService;
import com.evadeeva.eva.utils.AppConstants;

@RestController
@RequestMapping("/category")
public class CategoryRest {
	private CategoryService categoryService;

	public CategoryRest(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// create category
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}

	// get all category
	@GetMapping
	public CategoryResponse getAllCategories(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir
		){
		return categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir);
	}

	// get category by id
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	// update category
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable(name = "id") long id) {
		CategoryDto categoryResponse = categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	// delete
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockCategory(@PathVariable(name = "id") long id) {
		categoryService.lockCategoryById(id);
		return new ResponseEntity<>("Category entity delete successfully.", HttpStatus.OK);
	}

	// get all lock category
	@GetMapping("/lock")
	public List<CategoryDto> getLockCategories() {
		return categoryService.getLockCategories();
	}

	// unlock
	@PutMapping("/unlock/{id}")
	public ResponseEntity<String> unlockCategory(@PathVariable(name = "id") long id) {
		categoryService.unlockCategoryByStatusAndId(1, id);
		return new ResponseEntity<>("Category entity unlock successfully.", HttpStatus.OK);
	}

}
