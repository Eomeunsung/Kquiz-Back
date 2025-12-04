package com.back.kdquiz.quiz.service;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface QuizService {

    ResponseEntity quizCreateResponse(QuizCreateDto quizCreateDto, CustomUserDetails userDetails);

    ResponseEntity quizUpdateResponse(QuizUpdateDto quizUpdateDto);

    ResponseEntity quizGetResponse(Long quizId);

    ResponseEntity quizDeleteResponse(Long quizId);



}
