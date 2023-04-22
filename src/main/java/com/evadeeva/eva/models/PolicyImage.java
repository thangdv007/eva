package com.evadeeva.eva.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policy_image")
public class PolicyImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_image_id")
	private long id;
	
	@Column
	private String img;
	
	@Column
	private Date createdDate;
	
	@Column
	private Date modifiedDate;
	
	@Column
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id", nullable = false)
	private Policy policy;

}
