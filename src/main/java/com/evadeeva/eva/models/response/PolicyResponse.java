package com.evadeeva.eva.models.response;

import java.util.List;

import com.evadeeva.eva.models.dtos.PolicyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyResponse {
	
	private List<PolicyDto> content;
	
	private int pageNo;
	
	private int pageSize;
	
	private long totalElements;
	
	private int totalPages;
	
	private boolean Last;
}
