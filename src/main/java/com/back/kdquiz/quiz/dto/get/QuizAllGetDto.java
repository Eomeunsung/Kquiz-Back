package com.back.kdquiz.quiz.dto.get;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class QuizAllGetDto {
    private Long id;
    private String title;
    private String nickName;
    private LocalDateTime updateAt;

    public QuizAllGetDto() {
    }

    @Builder
    public QuizAllGetDto(Long id, String title, String nickName, LocalDateTime updateAt) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
        this.updateAt = updateAt;
    }
}
