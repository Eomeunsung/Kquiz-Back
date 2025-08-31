package com.back.kdquiz.game.dto.room;

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
