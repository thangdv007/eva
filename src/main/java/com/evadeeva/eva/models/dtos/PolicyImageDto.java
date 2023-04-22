package com.evadeeva.eva.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PolicyImageDto {
	private long id;

	@NotEmpty
	private String img;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private int status;
}
