package com.evadeeva.eva.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evadeeva.eva.exceptions.ResourceNotFoundException;
import com.evadeeva.eva.models.Banner;
import com.evadeeva.eva.models.Category;
import com.evadeeva.eva.models.dtos.BannerDto;
import com.evadeeva.eva.models.response.BannerResponse;
import com.evadeeva.eva.repositories.BannerRepository;
import com.evadeeva.eva.repositories.CategoryRepository;
import com.evadeeva.eva.services.BannerService;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;

	BannerServiceImpl(BannerRepository bannerRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
		this.bannerRepository = bannerRepository;
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
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

		return mapToDTO(newBanner);
	}

	// Lấy tất cả banner theo category id
	@Override
	public BannerResponse getBannerByCategoryId(long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// lấy banner theo categoryId
		Page<Banner> banners = bannerRepository.findByCategoryId(categoryId, pageable);

		// lấy nội dung cho đối tượng trang
		List<Banner> listOfBanners = banners.getContent();

		List<BannerDto> content = listOfBanners.stream().map(banner -> mapToDTO(banner))
				.collect(Collectors.toList());
		
		BannerResponse bannerResponse = new BannerResponse();
		
		bannerResponse.setContent(content);
		bannerResponse.setPageNo(banners.getNumber());
		bannerResponse.setPageSize(banners.getSize());
		bannerResponse.setTotalElements(banners.getTotalElements());
		bannerResponse.setTotalPages(banners.getTotalPages());
		bannerResponse.setLast(banners.isLast());
		
		return bannerResponse;
	}

	// delete
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
	public BannerResponse getLockBanners(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// lấy banner theo categoryId
		Page<Banner> banners = bannerRepository.findByStatus(0, pageable);

		// lấy nội dung cho đối tượng trang
		List<Banner> listOfBanners = banners.getContent();

		List<BannerDto> content = listOfBanners.stream().map(banner -> mapToDTO(banner))
				.collect(Collectors.toList());
		
		BannerResponse bannerResponse = new BannerResponse();
		
		bannerResponse.setContent(content);
		bannerResponse.setPageNo(banners.getNumber());
		bannerResponse.setPageSize(banners.getSize());
		bannerResponse.setTotalElements(banners.getTotalElements());
		bannerResponse.setTotalPages(banners.getTotalPages());
		bannerResponse.setLast(banners.isLast());
		
		return bannerResponse;
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
		BannerDto bannerDto = mapper.map(banner, BannerDto.class);

//		BannerDto bannerDto = new BannerDto();
//		bannerDto.setId(banner.getId());
//		bannerDto.setName(banner.getName());
//		bannerDto.setImage(banner.getImage());
//		bannerDto.setCreatedDate(banner.getCreatedDate());
//		bannerDto.setModifiedDate(banner.getModifiedDate());
//		bannerDto.setStatus(banner.getStatus());

		return bannerDto;
	}

	// chuyển DTO thành Entity
	private Banner mapToEntity(BannerDto bannerDto) {
		Banner banner = mapper.map(bannerDto, Banner.class);

//		Banner banner = new Banner();
//		banner.setId(bannerDto.getId());
//		banner.setName(bannerDto.getName());
//		banner.setImage(bannerDto.getImage());
//		banner.setCreatedDate(banner.getCreatedDate());
//		banner.setModifiedDate(bannerDto.getModifiedDate());
//		banner.setStatus(bannerDto.getStatus());

		return banner;
	}
}
