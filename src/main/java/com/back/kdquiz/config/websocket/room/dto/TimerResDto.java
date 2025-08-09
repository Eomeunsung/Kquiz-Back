package com.back.kdquiz.config.websocket.room.dto;

import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimerResDto {
    private TypeEnum type;
    private int timer;
}
