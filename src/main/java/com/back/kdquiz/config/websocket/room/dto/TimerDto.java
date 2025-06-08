package com.back.kdquiz.config.websocket.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerDto {
    private int time;
    private Boolean flag;
    private String type;
}
