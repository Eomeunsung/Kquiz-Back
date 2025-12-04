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
    private String nickName;
    private LocalDateTime updateAt;
}
