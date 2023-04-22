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
import org.springframework.web.bind.annotation.RestController;

import com.evadeeva.eva.models.dtos.PolicyImageDto;
import com.evadeeva.eva.services.PolicyImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorypolicy/policy/image")
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
	public List<PolicyImageDto> getPolicyImageByPolicyId(@PathVariable(value = "id") long policyId) {
		return policyImageService.getPolicyImageByPolicyId(policyId);
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
	public List<PolicyImageDto> getLockPolicyImages() {
		return policyImageService.getLockPolicyImages();
	}

	// unlock PolicyImage
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<?> unlockPolicyImageByStatusAndId(@PathVariable(value = "id") long policyImageId) {
		policyImageService.unlockPolicyImageByStatusAndId(1, policyImageId);
		return new ResponseEntity<>("PolicyImage entity unlock successfully", HttpStatus.OK);
	}

}
