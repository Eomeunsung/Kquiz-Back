package com.back.kdquiz.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceGetDto {
    private Long id;
    private String content;
    private Boolean isCorrect;

}
