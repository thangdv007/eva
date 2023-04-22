package com.evadeeva.eva.services;

import java.util.List;

import com.evadeeva.eva.models.dtos.PolicyDto;

public interface PolicyService {
	PolicyDto createPolicy(long categoryPolicyId, PolicyDto policyDto);
	
	List<PolicyDto> getPolicyByCategoryPolicyId(long categoryPolicyId);

	void lockPolicyById(long PolicyId);
	
	PolicyDto updatePolicy(PolicyDto policyDto, long id);
	
	List<PolicyDto> getLockPolicies();
	
	void unlockPolicyByStatusAndId(int status, long PolicyId);
	
}
