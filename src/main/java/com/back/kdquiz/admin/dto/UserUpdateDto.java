package com.back.kdquiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserUpdateDto {
    private Long userId;
    private String email;
    private String nickName;
    private Set<String> roles;


}
