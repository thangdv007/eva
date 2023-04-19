package com.evadeeva.eva.models.response;

import java.util.List;

import com.evadeeva.eva.models.dtos.BannerDto;
import com.evadeeva.eva.models.dtos.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponse {
	
	private List<BannerDto> content;
	
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
