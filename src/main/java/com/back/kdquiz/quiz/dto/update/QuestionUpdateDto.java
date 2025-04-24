package com.back.kdquiz.quiz.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionUpdateDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updateAt;
    private List<ChoiceUpdateDto> choices;
    private OptionUpdateDto option;

}
