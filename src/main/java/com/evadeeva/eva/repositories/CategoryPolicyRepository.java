package com.evadeeva.eva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.CategoryPolicy;

public interface CategoryPolicyRepository extends JpaRepository<CategoryPolicy, Long> {

	List<CategoryPolicy> findByStatus(int status);

	CategoryPolicy findByStatusAndId(int satatus, long id);

}
