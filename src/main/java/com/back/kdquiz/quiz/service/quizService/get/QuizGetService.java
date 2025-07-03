package com.back.kdquiz.quiz.service.quizService.get;

import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import org.springframework.http.ResponseEntity;

public interface QuizGetService {

    ResponseEntity quizGetResponse(Long quizId);

    QuizGetDto quizGetDto(Long quizId);
}
