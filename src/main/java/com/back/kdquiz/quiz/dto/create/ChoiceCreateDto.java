package com.back.kdquiz.quiz.dto.create;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class ChoiceCreateDto {
    private Long id;
    private String content;
    private Boolean isCorrect;
}
