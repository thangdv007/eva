package com.evadeeva.eva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Policy;
import com.evadeeva.eva.models.PolicyImage;

public interface PolicyImageRepository extends JpaRepository<PolicyImage, Long> {
	List<PolicyImage> findByPolicyId(long policyId);
	
	List<PolicyImage> findByStatus(int status);

}
