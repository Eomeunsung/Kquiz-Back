package com.back.kdquiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListDto {

    private List<UserGetDto> userList;
}
