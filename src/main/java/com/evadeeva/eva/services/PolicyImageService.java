package com.evadeeva.eva.services;

import java.util.List;

import com.evadeeva.eva.models.dtos.PolicyImageDto;

public interface PolicyImageService {
	PolicyImageDto createPolicyImage(long policyId, PolicyImageDto policyImageDto);
	
	List<PolicyImageDto> getPolicyImageByPolicyId(long policyId);

	void lockPolicyImageById(long PolicyImageId);
	
	List<PolicyImageDto> getLockPolicyImages();
	
	void unlockPolicyImageByStatusAndId(int status, long policyImageId);

	
	
}
