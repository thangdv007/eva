package com.evadeeva.eva.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evadeeva.eva.exceptions.ResourceNotFoundException;
import com.evadeeva.eva.models.Policy;
import com.evadeeva.eva.models.PolicyImage;
import com.evadeeva.eva.models.dtos.PolicyImageDto;
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
	public List<PolicyImageDto> getPolicyImageByPolicyId(long policyId) {
		// TODO Auto-generated method stub
		// Lấy PolicyImages theo PolicyId
		List<PolicyImage> policyImages = policyImageRepository.findByPolicyId(policyId);

		// Chuyển đổi list PolicyImage entity thành list PolicyImage dto
		return policyImages.stream().map(policyImage -> mapToDTO(policyImage)).collect(Collectors.toList());
	}

	//delete
	@Override
	public void lockPolicyImageById(long policyImageId) {
		// TODO Auto-generated method stub
		// Tìm policyimg theo id
		PolicyImage policyImage = policyImageRepository.findById(policyImageId)
				.orElseThrow(() -> new ResourceNotFoundException("PolicyImage", "id", policyImageId));
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
	public List<PolicyImageDto> getLockPolicyImages() {
		// TODO Auto-generated method stub
		List<PolicyImage> policyImages = policyImageRepository.findByStatus(0);
		return policyImages.stream().map(policyImage -> mapToDTO(policyImage)).collect(Collectors.toList());
	}

	// unlock PolicyImage
	@Override
	public void unlockPolicyImageByStatusAndId(int status, long policyImageId) {
		// TODO Auto-generated method stub
		// Tìm poicyimg theo id
		PolicyImage policyImage = policyImageRepository.findById(policyImageId)
				.orElseThrow(() -> new ResourceNotFoundException("PolicyImage", "id", policyImageId));
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
