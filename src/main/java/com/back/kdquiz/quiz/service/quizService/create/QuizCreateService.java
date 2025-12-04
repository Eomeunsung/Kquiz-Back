package com.back.kdquiz.quiz.service.quizService.create;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuizCreateService {

    ResponseDto quizCreate(QuizCreateDto quizCreateDto, CustomUserDetails userDetails);

    long quizCreateDto(QuizCreateDto quizCreateDto);
}
