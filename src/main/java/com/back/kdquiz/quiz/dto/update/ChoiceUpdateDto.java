package com.back.kdquiz.quiz.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceUpdateDto {
    private Long id;
    private String content;
    private Boolean isCorrect;
}
