package com.back.kdquiz.role.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
public class RoleGetDto {

    Set<String> roles;
}
