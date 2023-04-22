package com.evadeeva.eva.models.response;

import java.util.List;

import com.evadeeva.eva.models.dtos.CategoryPolicyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPolicyResponse {
	private List<CategoryPolicyDto> content;
	
	private int pageNo;
	
	private int pageSize;
	
	private long totalElements;
	
	private int totalPages;
	
	private boolean Last;
	
	/*
	 * private String title;
	 * 
	 * private int status;
	 * 
	 * private Date modifiedDate;
	 * 
	 * private Date createdDate;
	 * 
	 * private String description;
	 */
}
