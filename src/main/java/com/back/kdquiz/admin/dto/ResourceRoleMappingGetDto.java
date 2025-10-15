package com.back.kdquiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ResourceRoleMappingGetDto {
    private ResourceGetDto resource;
    private Set<RoleGetDto> roles;
}
