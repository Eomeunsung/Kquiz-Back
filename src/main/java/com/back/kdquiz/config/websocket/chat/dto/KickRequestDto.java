package com.back.kdquiz.config.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KickRequestDto {
    private String gameId;
    private String userId;
}
