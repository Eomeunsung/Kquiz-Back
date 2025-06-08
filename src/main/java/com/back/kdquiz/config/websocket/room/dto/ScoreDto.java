package com.back.kdquiz.config.websocket.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreDto {
    private int score;
    private String userId;
    private String type;
}
