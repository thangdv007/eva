package com.evadeeva.eva.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.CategoryPolicy;

public interface CategoryPolicyRepository extends JpaRepository<CategoryPolicy, Long> {

	Page<CategoryPolicy> findByUserId(long userId, PageRequest pageable);
	
	Page<CategoryPolicy> findByStatus(int status, PageRequest pageable);

	CategoryPolicy findByStatusAndId(int satatus, long id);

}
