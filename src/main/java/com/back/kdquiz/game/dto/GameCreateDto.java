package com.back.kdquiz.game.dto;

import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameCreateDto {
    private String gameId;
    private QuizGetDto quizGetDto;
}
