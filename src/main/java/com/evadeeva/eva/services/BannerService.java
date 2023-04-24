package com.evadeeva.eva.services;

import com.evadeeva.eva.models.dtos.BannerDto;
import com.evadeeva.eva.models.response.BannerResponse;

public interface BannerService {
	BannerDto createBanner(long categoryId, BannerDto bannerDto);
	
	BannerResponse getBannerByCategoryId(long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

	void lockBannerById(long bannerId);
	
	BannerResponse getLockBanners(int pageNo, int pageSize, String sortBy, String sortDir);
	
	void unlockBannerByStatusAndId(int status, long bannerId);
	
}
