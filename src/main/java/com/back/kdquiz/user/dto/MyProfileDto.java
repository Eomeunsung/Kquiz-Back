package com.back.kdquiz.user.dto;

import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
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
}
