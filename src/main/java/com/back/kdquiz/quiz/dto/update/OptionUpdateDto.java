package com.back.kdquiz.quiz.dto.update;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.StyledEditorKit;

@Getter
@Setter
public class OptionUpdateDto {
    private Long id;
    private int time;
    private Boolean useAiFeedBack;
    private String aiQuestion;
    private String commentary;
    private int score;
}
