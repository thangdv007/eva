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

import com.evadeeva.eva.models.dtos.PolicyDto;
import com.evadeeva.eva.models.response.PolicyResponse;
import com.evadeeva.eva.services.PolicyService;
import com.evadeeva.eva.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user/categorypolicy/policy")
public class PolicyRest {
	private PolicyService policyService;

	public PolicyRest(PolicyService policyService) {
		this.policyService = policyService;
	}

	// create policy
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create/{id}")
	public ResponseEntity<PolicyDto> createpolicy(@Valid @PathVariable(value = "id") long categoryPolicyId,
			@RequestBody PolicyDto policyDto) {

		return new ResponseEntity<>(policyService.createPolicy(categoryPolicyId, policyDto), HttpStatus.CREATED);
	}

	// get
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public PolicyResponse getpolicysByCategoryId(@PathVariable(value = "id") long categoryPolicyId,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return policyService.getPolicyByCategoryPolicyId(categoryPolicyId, pageNo, pageSize, sortBy, sortDir);
	}

	// update
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PolicyDto> updatePolicy(@Valid @RequestBody PolicyDto policyDto,
			@PathVariable(name = "id") long id) {
		PolicyDto policyResponse = policyService.updatePolicy(policyDto, id);
		return new ResponseEntity<>(policyResponse, HttpStatus.OK);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockPolicyById(@PathVariable(value = "id") long policyId) {
		policyService.lockPolicyById(policyId);
		return new ResponseEntity<>("Policy entity delete successfully", HttpStatus.OK);
	}

	// get all policy lock
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lock")
	public PolicyResponse getLockpolicies(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DEREACTION, required = false) String sortDir) {
		return policyService.getLockPolicies(pageNo, pageSize, sortBy, sortDir);
	}

	// unlock policy
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<?> unlockPolicyByStatusAndId(@PathVariable(value = "id") long policyId) {
		policyService.unlockPolicyByStatusAndId(1, policyId);
		return new ResponseEntity<>("Policy entity unlock successfully", HttpStatus.OK);
	}

}
