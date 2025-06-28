package com.back.kdquiz.user.dto;

import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MyProfileDto {
    private String email;
    private String nickName;
    private LocalDate createAt;
    private List<QuizAllGetDto> quizList;

    public MyProfileDto() {
    }

    @Builder
    public MyProfileDto(String email, String nickName, LocalDate createAt, List<QuizAllGetDto> quizList) {
        this.email = email;
        this.nickName = nickName;
        this.createAt = createAt;
        this.quizList = quizList;
    }
}
