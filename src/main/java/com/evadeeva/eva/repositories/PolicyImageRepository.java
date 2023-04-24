package com.evadeeva.eva.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.PolicyImage;

public interface PolicyImageRepository extends JpaRepository<PolicyImage, Long> {
	Page<PolicyImage> findByPolicyId(long policyId, PageRequest pageable);
	
	Page<PolicyImage> findByStatus(int status, PageRequest pageable);

}
