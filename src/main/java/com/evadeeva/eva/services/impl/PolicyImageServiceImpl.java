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
import com.evadeeva.eva.models.Policy;
import com.evadeeva.eva.models.PolicyImage;
import com.evadeeva.eva.models.dtos.PolicyImageDto;
import com.evadeeva.eva.models.response.PolicyImageResponse;
import com.evadeeva.eva.repositories.PolicyImageRepository;
import com.evadeeva.eva.repositories.PolicyRepository;
import com.evadeeva.eva.services.PolicyImageService;

@Service
public class PolicyImageServiceImpl implements PolicyImageService {

	@Autowired
	private  PolicyImageRepository policyImageRepository;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private ModelMapper mapper;

	PolicyImageServiceImpl(PolicyImageRepository policyImageRepository, PolicyRepository policyRepository, ModelMapper mapper) {
		this.policyImageRepository = policyImageRepository;
		this.policyRepository = policyRepository;
		this.mapper = mapper;
	}

	// Create
	@Override
	public PolicyImageDto createPolicyImage(long policyId, PolicyImageDto policyImageDto) {
		// TODO Auto-generated method stub
		// chuyển đổi DTO thành Entity
		PolicyImage policyImage = mapToEntity(policyImageDto);
		// Lấy Policy theo id
		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));
		// đặt Policy thành PolicyImage entity
		policyImage.setPolicy(policy);

		policyImage.setStatus(1);
		Date currentDateTime = new Date();
		policyImage.setCreatedDate(currentDateTime);

		PolicyImage newPolicyImage = policyImageRepository.save(policyImage);

		return mapToDTO(newPolicyImage);
	}

	// Lấy tất cả PolicyImage theo Policy id
	@Override
	public PolicyImageResponse getPolicyImageByPolicyId(long policyId, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// tìm tất cả
		
		Page<PolicyImage> policyImages = policyImageRepository.findByPolicyId(policyId, pageable);

		// lấy nội dung cho đối tượng trang
		List<PolicyImage> listOfPolicyImages = policyImages.getContent();

		List<PolicyImageDto> content = listOfPolicyImages.stream()
				.map(policyImage -> mapToDTO(policyImage)).collect(Collectors.toList());

		PolicyImageResponse policyImageResponse = new PolicyImageResponse();

		policyImageResponse.setContent(content);
		policyImageResponse.setPageNo(policyImages.getNumber());
		policyImageResponse.setPageSize(policyImages.getSize());
		policyImageResponse.setTotalElements(policyImages.getTotalElements());
		policyImageResponse.setTotalPages(policyImages.getTotalPages());
		policyImageResponse.setLast(policyImages.isLast());

		return policyImageResponse;
	}

	//delete
	@Override
	public void lockPolicyImageById(long id) {
		// TODO Auto-generated method stub
		// Tìm policyimg theo id
		PolicyImage policyImage = policyImageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PolicyImage", "id", id));
		// thay đổi trạng thái
		policyImage.setStatus(0);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		policyImage.setModifiedDate(currentDateTime);
		// lưu vào csdl
		policyImageRepository.save(policyImage);

	}

	// lấy danh sách delete
	@Override
	public PolicyImageResponse getLockPolicyImages(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		// tạo trường hợp có thể phân trang
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		// tìm tất cả
		
		Page<PolicyImage> policyImages = policyImageRepository.findByStatus(0, pageable);

		// lấy nội dung cho đối tượng trang
		List<PolicyImage> listOfPolicyImages = policyImages.getContent();

		List<PolicyImageDto> content = listOfPolicyImages.stream()
				.map(policyImage -> mapToDTO(policyImage)).collect(Collectors.toList());

		PolicyImageResponse policyImageResponse = new PolicyImageResponse();

		policyImageResponse.setContent(content);
		policyImageResponse.setPageNo(policyImages.getNumber());
		policyImageResponse.setPageSize(policyImages.getSize());
		policyImageResponse.setTotalElements(policyImages.getTotalElements());
		policyImageResponse.setTotalPages(policyImages.getTotalPages());
		policyImageResponse.setLast(policyImages.isLast());

		return policyImageResponse;
	}

	// unlock PolicyImage
	@Override
	public void unlockPolicyImageByStatusAndId(int status, long id) {
		// TODO Auto-generated method stub
		// Tìm poicyimg theo id
		PolicyImage policyImage = policyImageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PolicyImage", "id", id));
		// thay đổi trạng thái
		policyImage.setStatus(1);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		policyImage.setModifiedDate(currentDateTime);
		// lưu vào csdl
		policyImageRepository.save(policyImage);
	}

	// chuyển Entity thành DTO
	private PolicyImageDto mapToDTO(PolicyImage policyImage) {
		PolicyImageDto policyImageDto = mapper.map(policyImage, PolicyImageDto.class);

		return policyImageDto;
	}

	// chuyển DTO thành Entity
	private PolicyImage mapToEntity(PolicyImageDto policyImageDto) {
		PolicyImage policyImage = mapper.map(policyImageDto, PolicyImage.class);

		return policyImage;
	}
}
