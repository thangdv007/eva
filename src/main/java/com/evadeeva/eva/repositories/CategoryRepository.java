package com.evadeeva.eva.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	Page<Category> findByUserId(long userId, PageRequest pageable);
	
//	List<Category> findByStatus(int status);

	Category findByStatusAndId(int status, long id);

	Page<Category> findByStatus(int status, PageRequest pageable);

}
