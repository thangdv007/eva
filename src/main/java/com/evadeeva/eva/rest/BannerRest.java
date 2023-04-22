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

import com.evadeeva.eva.models.dtos.BannerDto;
import com.evadeeva.eva.services.BannerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category/banner")
public class BannerRest {
	private BannerService bannerService;

	public BannerRest(BannerService bannerService) {
		this.bannerService = bannerService;
	}

	// create banner
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create/{id}")
	public ResponseEntity<BannerDto> createBanner(@Valid @PathVariable(value = "id") long categoryId,
			@RequestBody BannerDto bannerDto) {

		return new ResponseEntity<>(bannerService.createBanner(categoryId, bannerDto), HttpStatus.CREATED);
	}

	// get
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public List<BannerDto> getBannerByCategoryId(@PathVariable(value = "id") long categoryId) {
		return bannerService.getBannerByCategoryId(categoryId);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> lockBannerById(@PathVariable(value = "id") long bannerId) {
		bannerService.lockBannerById(bannerId);
		return new ResponseEntity<>("Banner entity delete successfully", HttpStatus.OK);
	}

	// get all banner lock
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lock")
	public List<BannerDto> getLockBanners() {
		return bannerService.getLockBanners();
	}

	// unlock banner
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/unlock/{id}")
	public ResponseEntity<?> unlockBannerByStatusAndId(@PathVariable(value = "id") long bannerId) {
		bannerService.unlockBannerByStatusAndId(1, bannerId);
		return new ResponseEntity<>("Banner entity unlock successfully", HttpStatus.OK);
	}

}
