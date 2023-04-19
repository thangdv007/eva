package com.evadeeva.eva.models.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryDto {
	private long id;
	
	private String title;
	
	private String description;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private int status;
}
