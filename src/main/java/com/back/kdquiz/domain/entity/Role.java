package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority ,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String roleName;

    @ManyToMany(mappedBy = "userRoles", cascade = CascadeType.ALL)
    private Set<Users> usersRole = new HashSet<>();

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roleSet", cascade = CascadeType.ALL)
    private Set<Resources> resourcesSet = new LinkedHashSet<>();
}
