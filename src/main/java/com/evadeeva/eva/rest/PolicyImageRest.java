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

import com.evadeeva.eva.models.dtos.PolicyImageDto;
import com.evadeeva.eva.models.response.PolicyImageResponse;
import com.evadeeva.eva.services.PolicyImageService;
import com.evadeeva.eva.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user/categorypolicy/policy/image")
public class PolicyImageRest {
	private PolicyImageService policyImageService;

	public PolicyImageRest(PolicyImageService policyImageService) {
		this.policyImageService = policyImageService;
	}

	// create PolicyImage
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create/{id}")
	public ResponseEntity<PolicyImageDto> createPolicyImage(@Valid @PathVariable(value = "id") long policyId,
			@RequestBody PolicyImageDto policyImageDto) {

		return new ResponseEntity<>(policyImageService.createPolicyImage(policyId, policyImageDto), HttpStatus.CREATED);
	}

	// get
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public PolicyImageResponse getPolicyImageByPolicyId(@PathVariable(value = "id") long policyId,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return policyImageService.getPolicyImageByPolicyId(policyId, pageNo, pageSize, sortBy, sortDir);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockPolicyImageById(@PathVariable(value = "id") long policyImageId) {
		policyImageService.lockPolicyImageById(policyImageId);
		return new ResponseEntity<>("PolicyImage entity delete successfully", HttpStatus.OK);
	}

	// get all PolicyImage lock
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lock")
	public PolicyImageResponse getLockPolicyImages(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return policyImageService.getLockPolicyImages(pageNo, pageSize, sortBy, sortDir);
	}

	// unlock PolicyImage
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<?> unlockPolicyImageByStatusAndId(@PathVariable(value = "id") long policyImageId) {
		policyImageService.unlockPolicyImageByStatusAndId(1, policyImageId);
		return new ResponseEntity<>("PolicyImage entity unlock successfully", HttpStatus.OK);
	}

}
