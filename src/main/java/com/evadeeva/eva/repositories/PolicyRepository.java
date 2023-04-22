package com.evadeeva.eva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Policy;


public interface PolicyRepository extends JpaRepository<Policy, Long> {
	List<Policy> findByCategoryPolicyId(long categoryPolicyId);

	List<Policy> findByStatus(int status);
}
