package com.evadeeva.eva.services;

import java.util.List;

import com.evadeeva.eva.models.dtos.BannerDto;

public interface BannerService {
	BannerDto createBanner(long categoryId, BannerDto bannerDto);
	
	List<BannerDto> getBannerByCategoryId(long categoryId);

	void lockBannerById(long bannerId);
	
	List<BannerDto> getLockBanners();
	
	void unlockBannerByStatusAndId(int status, long bannerId);
	
}
