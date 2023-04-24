package com.evadeeva.eva.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Policy;


public interface PolicyRepository extends JpaRepository<Policy, Long> {
	Page<Policy> findByCategoryPolicyId(long categoryPolicyId, PageRequest pageable);

	Page<Policy> findByStatus(int status, PageRequest pageable);
}
