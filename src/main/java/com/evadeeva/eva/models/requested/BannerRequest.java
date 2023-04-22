package com.evadeeva.eva.models.requested;

import java.util.Date;

import lombok.Data;

@Data
public class BannerRequest {
	private long id;

	private String name;

	private String image;

	private Date modifiedDate;

	private Date createdDate;
}
