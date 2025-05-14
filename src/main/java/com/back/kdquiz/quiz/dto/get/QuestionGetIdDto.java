package com.back.kdquiz.quiz.dto.get;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class QuestionGetIdDto {
    private Long quizId;
    private String title;
    private List<Long> questionId;
}
