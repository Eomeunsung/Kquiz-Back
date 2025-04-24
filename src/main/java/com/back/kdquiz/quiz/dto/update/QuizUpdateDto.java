package com.back.kdquiz.quiz.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuizUpdateDto {
    private Long id;
    private String title;
    private LocalDateTime updateAt;
    private List<QuestionUpdateDto> questions;

}
