package com.evadeeva.eva.models.requested;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryRequest {
	private String title;
	
	private int status;
	
	private Date modifiedDate;

	private Date createdDate;
	 
	 private String description;

}
