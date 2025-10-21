package com.back.kdquiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserGetDto {

    private Long id;
    private String email;
    private String nickName;
    private Boolean enabled;
    private LocalDate createAt;
    private Set<String> roles;

    public UserGetDto() {
    }

    public UserGetDto(Long id, String email, String nickName, Boolean enabled, LocalDate createAt, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.enabled = enabled;
        this.createAt = createAt;
        this.roles = roles;
    }
}
