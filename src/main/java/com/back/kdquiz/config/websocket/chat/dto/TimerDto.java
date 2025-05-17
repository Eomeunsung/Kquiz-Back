package com.back.kdquiz.config.websocket.chat.dto;

import com.back.kdquiz.config.websocket.chat.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerDto {
    private int time;
    private Boolean flag;
    private String type;
}
