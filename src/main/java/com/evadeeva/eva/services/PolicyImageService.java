package com.evadeeva.eva.services;

import com.evadeeva.eva.models.dtos.PolicyImageDto;
import com.evadeeva.eva.models.response.PolicyImageResponse;

public interface PolicyImageService {
	PolicyImageDto createPolicyImage(long policyId, PolicyImageDto policyImageDto);
	
	PolicyImageResponse getPolicyImageByPolicyId(long policyId, int pageNo, int pageSize, String sortBy, String sortDir);

	void lockPolicyImageById(long id);
	
	PolicyImageResponse getLockPolicyImages(int pageNo, int pageSize, String sortBy, String sortDir);
	
	void unlockPolicyImageByStatusAndId(int status, long id);

	
	
}
