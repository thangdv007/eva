package com.evadeeva.eva.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(targetEntity =  User.class, mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
}
