package com.back.kdquiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MappingUpdateDto {
    private Long resourceId;
    private Set<Long> roleId = new HashSet<>();
}
