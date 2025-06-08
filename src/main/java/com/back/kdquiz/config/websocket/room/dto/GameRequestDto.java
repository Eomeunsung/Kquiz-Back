package com.back.kdquiz.config.websocket.room.dto;

import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRequestDto {
    private String content;
    private TypeEnum type;
}
