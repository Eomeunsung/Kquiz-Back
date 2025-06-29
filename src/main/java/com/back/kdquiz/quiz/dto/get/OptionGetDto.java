package com.back.kdquiz.quiz.dto.get;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionGetDto {
    private Long id;
    private int time;
    private Boolean useAiFeedBack;
    private String aiQuestion;
    private Boolean useCommentary;
    private String commentary;
    private int score;


    public OptionGetDto() {
    }

    @Builder
    public OptionGetDto(Long id, int time, Boolean useAiFeedBack, String aiQuestion, Boolean useCommentary, String commentary, int score) {
        this.id = id;
        this.time = time;
        this.useAiFeedBack = useAiFeedBack;
        this.aiQuestion = aiQuestion;
        this.useCommentary = useCommentary;
        this.commentary = commentary;
        this.score = score;
    }
}
