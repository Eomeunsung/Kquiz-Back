package com.back.kdquiz.quiz.dto.create;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceCreateDto {
    private Long id;
    private String content;
    private Boolean isCorrect;

    public ChoiceCreateDto() {
    }

    @Builder
    public ChoiceCreateDto(Long id, String content, Boolean isCorrect) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
    }
}
