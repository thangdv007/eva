package com.evadeeva.eva.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
	private long id;
	
	@NotEmpty
	@Size(min =2, message = "Category title should have at least 2 characters")
	private String title;
	@NotEmpty
	@Size(min =10, message = "Category description should have at least 10 characters")
	private String description;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private int status;
	
	private long userId;
	
//	private Set<BannerDto> banners;
}
