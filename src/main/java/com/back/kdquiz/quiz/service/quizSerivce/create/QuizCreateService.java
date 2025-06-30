package com.back.kdquiz.quiz.service.quizSerivce.create;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import org.springframework.http.ResponseEntity;

public interface QuizCreateService {

    ResponseEntity quizCreateResponse(QuizCreateDto quizCreateDto, CustomUserDetails userDetails);

    long quizCreateDto(QuizCreateDto quizCreateDto);
}
