package com.evadeeva.eva.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.evadeeva.eva.exception.BlogAPIException;
import com.evadeeva.eva.exception.ResourceNotFoundException;
import com.evadeeva.eva.models.Banner;
import com.evadeeva.eva.models.Category;
import com.evadeeva.eva.models.dtos.BannerDto;
import com.evadeeva.eva.repositories.BannerRepository;
import com.evadeeva.eva.repositories.CategoryRepository;
import com.evadeeva.eva.service.BannerService;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private final BannerRepository bannerRepository;

	private final CategoryRepository categoryRepository;

	BannerServiceImpl(BannerRepository bannerRepository, CategoryRepository categoryRepository) {
		this.bannerRepository = bannerRepository;
		this.categoryRepository = categoryRepository;
	}

	// Create
	@Override
	public BannerDto createBanner(long categoryId, BannerDto bannerDto) {
		// TODO Auto-generated method stub
		// chuyển đổi DTO thành Entity
		Banner banner = mapToEntity(bannerDto);
		// Lấy category theo id
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		// đặt category thành banner entity
		banner.setCategory(category);

		banner.setStatus(1);
		Date currentDateTime = new Date();
		banner.setCreatedDate(currentDateTime);

		Banner newBanner = bannerRepository.save(banner);
		// chuyển Entity thành DTO
		BannerDto bannerResponse = mapToDTO(newBanner);

		return mapToDTO(newBanner);
	}

	// Lấy tất cả banner theo category id
	@Override
	public List<BannerDto> getBannerByCategoryId(long categoryId) {
		// TODO Auto-generated method stub
		// Lấy banners theo categoryId
		List<Banner> banners = bannerRepository.findByCategoryId(categoryId);

		// Chuyển đổi list banner entity thành list banner dto
		return banners.stream().map(banner -> mapToDTO(banner)).collect(Collectors.toList());
	}

	@Override
	public void lockBannerById(long bannerId) {
		// TODO Auto-generated method stub
		// Tìm baner theo id
		Banner banner = bannerRepository.findById(bannerId)
				.orElseThrow(() -> new ResourceNotFoundException("Banner", "id", bannerId));
		// thay đổi trạng thái
		banner.setStatus(0);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		banner.setModifiedDate(currentDateTime);
		// lưu vào csdl
		bannerRepository.save(banner);

	}

	// lấy danh sách delete
	@Override
	public List<BannerDto> getLockBanners() {
		// TODO Auto-generated method stub
		List<Banner> banners = bannerRepository.findByStatus(0);
		return banners.stream().map(banner -> mapToDTO(banner)).collect(Collectors.toList());
	}

	// unlock banner
	@Override
	public void unlockBannerByStatusAndId(int status, long bannerId) {
		// TODO Auto-generated method stub
		// Tìm baner theo id
		Banner banner = bannerRepository.findById(bannerId)
				.orElseThrow(() -> new ResourceNotFoundException("Banner", "id", bannerId));
		// thay đổi trạng thái
		banner.setStatus(1);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		banner.setModifiedDate(currentDateTime);
		// lưu vào csdl
		bannerRepository.save(banner);
	}

	// chuyển Entity thành DTO
	private BannerDto mapToDTO(Banner banner) {
		BannerDto bannerDto = new BannerDto();

		bannerDto.setId(banner.getId());
		bannerDto.setName(banner.getName());
		bannerDto.setImage(banner.getImage());
		bannerDto.setCreatedDate(banner.getCreatedDate());
		bannerDto.setModifiedDate(banner.getModifiedDate());
		bannerDto.setStatus(banner.getStatus());

		return bannerDto;
	}

	// chuyển DTO thành Entity
	private Banner mapToEntity(BannerDto bannerDto) {
		Banner banner = new Banner();

		banner.setId(bannerDto.getId());
		banner.setName(bannerDto.getName());
		banner.setImage(bannerDto.getImage());
		banner.setCreatedDate(banner.getCreatedDate());
		banner.setModifiedDate(bannerDto.getModifiedDate());
		banner.setStatus(bannerDto.getStatus());

		return banner;
	}
}
