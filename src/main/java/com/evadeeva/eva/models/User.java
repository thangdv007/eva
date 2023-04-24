package com.evadeeva.eva.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column
    private String fullname;

    @Column
    private String phone;

    @Column
    private String image;

    @Column
    private Date createdDate;

    @Column
    private Date modifiedDate;

    @Column(nullable = false)
    private int status;

    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<CategoryPolicy> categoriesPolicy = new HashSet<>();
}
