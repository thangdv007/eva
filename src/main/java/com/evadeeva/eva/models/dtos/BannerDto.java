package com.evadeeva.eva.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BannerDto {
	private long id;
	@NotEmpty
	@Size(min =2, message = "Banner name should have at least 2 characters")
	private String name;
	 @NotEmpty
	private String image;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private int status;
	
	private long categoryId;
}
