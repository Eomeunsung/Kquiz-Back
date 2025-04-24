package com.back.kdquiz.quiz.dto.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionGetDto {
    private Long id;
    private int time;
    private Boolean useAiFeedBack;
    private String aiQuestion;
    private String commentary;
    private int score;
}
