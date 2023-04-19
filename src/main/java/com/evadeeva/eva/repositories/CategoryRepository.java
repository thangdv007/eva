package com.evadeeva.eva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evadeeva.eva.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findByStatus(int status);

	Category findByStatusAndId(int status, long id);

}
