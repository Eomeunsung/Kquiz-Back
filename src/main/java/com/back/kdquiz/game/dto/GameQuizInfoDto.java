package com.back.kdquiz.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameQuizInfoDto {
    private String quizTitle;
    private int questionSize;
}
