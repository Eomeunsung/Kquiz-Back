package com.back.kdquiz.quiz.service.quizService.update;

import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import org.springframework.http.ResponseEntity;

public interface QuizUpdateService {
    ResponseEntity quizUpdateResponse(QuizUpdateDto quizUpdateDto);
}
