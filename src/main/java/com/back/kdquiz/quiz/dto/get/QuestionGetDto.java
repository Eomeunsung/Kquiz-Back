package com.back.kdquiz.quiz.dto.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionGetDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<ChoiceGetDto> choices;
    private OptionGetDto option;
}
