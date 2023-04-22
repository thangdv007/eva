package com.evadeeva.eva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
	List<Banner> findByCategoryId(long categoryId);
	
	List<Banner> findByStatus(int status);
}
