package com.evadeeva.eva.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policy")
public class Policy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_id")
	private long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column
	private String content;
	
	@Column
	private Date createdDate;
	
	@Column
	private Date modifiedDate;
	
	@Column(nullable = false)
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_policy_id", nullable = false)
	private CategoryPolicy categoryPolicy;
	
	@OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
	private Set<PolicyImage> policyImages = new HashSet<>();
}
