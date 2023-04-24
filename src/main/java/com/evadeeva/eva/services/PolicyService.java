package com.evadeeva.eva.services;

import com.evadeeva.eva.models.dtos.PolicyDto;
import com.evadeeva.eva.models.response.PolicyResponse;

public interface PolicyService {
	PolicyDto createPolicy(long categoryPolicyId, PolicyDto policyDto);
	
	PolicyResponse getPolicyByCategoryPolicyId(long categoryPolicyId, int pageNo, int pageSize, String sortBy, String sortDir);

	void lockPolicyById(long PolicyId);
	
	PolicyDto updatePolicy(PolicyDto policyDto, long id);
	
	PolicyResponse getLockPolicies(int pageNo, int pageSize, String sortBy, String sortDir);
	
	void unlockPolicyByStatusAndId(int status, long id);
	
}
