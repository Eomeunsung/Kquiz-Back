package com.back.kdquiz.quiz.dto.get;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizAllGetDto {
    private Long id;
    private String title;

    @Builder.Default
    private String nickName = "default";
    private LocalDateTime updateAt;
}
