package com.evadeeva.eva.models.response;

import java.util.List;

import com.evadeeva.eva.models.dtos.PolicyImageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyImageResponse {
	
	private List<PolicyImageDto> content;
	
	private int pageNo;
	
	private int pageSize;
	
	private long totalElements;
	
	private int totalPages;
	
	private boolean Last;
	
	/*
	 * private String name;
	 * 
	 * private String imgage;
	 * 
	 * private int status;
	 * 
	 * private Date modifiedDate;
	 * 
	 * private Date createdDate;
	 * 
	 */
}
