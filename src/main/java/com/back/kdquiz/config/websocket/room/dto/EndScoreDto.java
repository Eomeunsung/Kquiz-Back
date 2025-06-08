package com.back.kdquiz.config.websocket.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EndScoreDto {
    private String username;
    private int score;
}
