package com.evadeeva.eva.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
	Page<Banner> findByCategoryId(long categoryId, PageRequest pageable);
	
	Page<Banner> findByStatus(int status, PageRequest pageable);
}
