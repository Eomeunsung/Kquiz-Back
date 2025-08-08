package com.back.kdquiz.config.websocket.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LobbyReqDto {
    private String Content;
}
