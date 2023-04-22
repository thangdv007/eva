package com.evadeeva.eva.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evadeeva.eva.exceptions.ResourceNotFoundException;
import com.evadeeva.eva.models.CategoryPolicy;
import com.evadeeva.eva.models.Policy;
import com.evadeeva.eva.models.dtos.PolicyDto;
import com.evadeeva.eva.repositories.CategoryPolicyRepository;
import com.evadeeva.eva.repositories.PolicyRepository;
import com.evadeeva.eva.services.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private CategoryPolicyRepository categoryPolicyRepository;
	@Autowired
	private ModelMapper mapper;

	PolicyServiceImpl(PolicyRepository policyRepository, CategoryPolicyRepository categoryPolicyRepository,
			ModelMapper mapper) {
		this.policyRepository = policyRepository;
		this.categoryPolicyRepository = categoryPolicyRepository;
		this.mapper = mapper;
	}

	// Create
	@Override
	public PolicyDto createPolicy(long categoryPolicyId, PolicyDto policyDto) {
		// TODO Auto-generated method stub
		// chuyển đổi DTO thành Entity
		Policy policy = mapToEntity(policyDto);
		// Lấy policy theo id
		CategoryPolicy categoryPolicy = categoryPolicyRepository.findById(categoryPolicyId)
				.orElseThrow(() -> new ResourceNotFoundException("CategoryPolicy", "id", categoryPolicyId));
		// đặt categorypolicy thành policy entity
		policy.setCategoryPolicy(categoryPolicy);
		// đặt giá trị trạng thái mặc định là 1(hiện)
		policy.setStatus(1);
		Date currentDateTime = new Date();
		policy.setCreatedDate(currentDateTime);

		Policy newPolicy = policyRepository.save(policy);

		return mapToDTO(newPolicy);
	}

	// Lấy tất cả policy theo category id
	@Override
	public List<PolicyDto> getPolicyByCategoryPolicyId(long categoryPolicyId) {
		// TODO Auto-generated method stub
		// Lấy policies theo categoryId
		List<Policy> policies = policyRepository.findByCategoryPolicyId(categoryPolicyId);

		// Chuyển đổi list policies entity thành list policy dto
		return policies.stream().map(policy -> mapToDTO(policy)).collect(Collectors.toList());
	}

	//update
	@Override
	public PolicyDto updatePolicy(PolicyDto policyDto, long id) {
		// Lấy policy theo id
		Policy policy = policyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Policy", "id", id));
		Date currentDateTime = new Date();
		policy.setCreatedDate(currentDateTime);
		
		Policy updatedPolicy = policyRepository.save(policy);
		
		return mapToDTO(updatedPolicy);
		
	}

	// delete
	@Override
	public void lockPolicyById(long policyId) {
		// TODO Auto-generated method stub
		// Tìm policy theo id
		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));
		// thay đổi trạng thái
		policy.setStatus(0);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		policy.setModifiedDate(currentDateTime);
		// lưu vào csdl
		policyRepository.save(policy);

	}

	// lấy danh sách delete
	@Override
	public List<PolicyDto> getLockPolicies() {
		// TODO Auto-generated method stub
		List<Policy> policies = policyRepository.findByStatus(0);
		return policies.stream().map(policy -> mapToDTO(policy)).collect(Collectors.toList());
	}

	// unlock policy
	@Override
	public void unlockPolicyByStatusAndId(int status, long policyId) {
		// TODO Auto-generated method stub
		// Tìm policy theo id
		Policy policy = policyRepository.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));
		// thay đổi trạng thái
		policy.setStatus(1);
		// thời gian cập nhật
		Date currentDateTime = new Date();
		policy.setModifiedDate(currentDateTime);
		// lưu vào csdl
		policyRepository.save(policy);
	}

	// chuyển Entity thành DTO
	private PolicyDto mapToDTO(Policy policy) {
		PolicyDto policyDto = mapper.map(policy, PolicyDto.class);

		return policyDto;
	}

	// chuyển DTO thành Entity
	private Policy mapToEntity(PolicyDto policyDto) {
		Policy policy = mapper.map(policyDto, Policy.class);

		return policy;
	}
}
