package com.back.kdquiz.quiz.dto;

import com.back.kdquiz.domain.entity.Option;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionGetDto {
    private Long id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<ChoiceGetDto> choiceList;
    private OptionGetDto option;
}
