package com.evadeeva.eva.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banner")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banner_id")
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String image;
	
	@Column
	private Date createdDate;
	
	@Column
	private Date modifiedDate;
	
	@Column(nullable = false)
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
}
