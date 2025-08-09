package com.back.kdquiz.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameCreateDto {
    private String gameId;
    private String quizTitle;
    private Long questionSize;
    private Long userId;
}
