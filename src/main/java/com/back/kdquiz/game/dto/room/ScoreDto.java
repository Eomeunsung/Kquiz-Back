package com.back.kdquiz.game.dto.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreDto {
    private int score;
    private String userId;
    private String type;
}
