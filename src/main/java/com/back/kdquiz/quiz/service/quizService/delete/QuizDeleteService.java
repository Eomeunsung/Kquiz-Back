package com.back.kdquiz.quiz.service.quizService.delete;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface QuizDeleteService {

    ResponseEntity quizDeleteResponse(Long quizId) throws IOException;


}
