package com.back.kdquiz.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String token;
    private String nickName;

    public TokenDto() {
    }

    public TokenDto(String token, String nickName) {
        this.token = token;
        this.nickName = nickName;
    }
}
