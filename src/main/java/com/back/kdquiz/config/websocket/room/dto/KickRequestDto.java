package com.back.kdquiz.config.websocket.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KickRequestDto {
    private String gameId;
    private String userId;
}
