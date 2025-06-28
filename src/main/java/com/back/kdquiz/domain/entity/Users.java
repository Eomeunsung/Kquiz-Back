package com.back.kdquiz.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    private String password;

    @Column
    private Boolean enabled;

    @Column
    private LocalDate createAt;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Set<Quiz> quizList = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="users_roles", joinColumns = {@JoinColumn(name="users_id")}, inverseJoinColumns = {@JoinColumn(name="role_id")})
    private Set<Role> userRoles = new HashSet<>();

    public Users() {
    }

    @Builder
    public Users(String email, String nickName, String password, Boolean enabled, LocalDate createAt, Set<Role> userRoles) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.enabled = enabled;
        this.createAt = createAt;
        this.userRoles = userRoles;
    }

}
