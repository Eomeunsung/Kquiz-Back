package com.back.kdquiz.admin.dto;

import com.back.kdquiz.admin.dto.resourceDto.ResourceGetDto;
import com.back.kdquiz.admin.dto.roleDto.RoleGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MappingGetDto {
    private ResourceGetDto resource;
    private Set<RoleGetDto> roles;
}
