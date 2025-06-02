package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String roleName;

    @ManyToMany(mappedBy = "userRoles", cascade = CascadeType.ALL)
    private Set<User> userRole = new HashSet<>();
}
