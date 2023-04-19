package com.evadeeva.eva.models.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class BannerDto {
	private long id;
	
	private String name;
	 
	private String image;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private int status;
}
