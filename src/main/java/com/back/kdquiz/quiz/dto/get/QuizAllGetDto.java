package com.back.kdquiz.quiz.dto.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuizAllGetDto {
    private Long id;
    private String title;
    private LocalDateTime updateAt;
}
