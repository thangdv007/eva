package com.evadeeva.eva.mapper;

import org.mapstruct.Mapper;

import com.evadeeva.eva.models.Banner;
import com.evadeeva.eva.models.requested.BannerRequest;
import com.evadeeva.eva.models.response.BannerResponse;

@Mapper
public interface BannerMapper {
	Banner mapBannerToModel(BannerRequest bannerRequest);
	
	BannerResponse mapModelToResponse(Banner banner);
}
