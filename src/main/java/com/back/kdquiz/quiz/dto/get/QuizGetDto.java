package com.back.kdquiz.quiz.dto.get;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizGetDto {
    private Long id;
    private String title;
    private List<QuestionGetDto> questions;
}
