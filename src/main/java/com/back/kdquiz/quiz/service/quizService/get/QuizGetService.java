package com.back.kdquiz.quiz.service.quizService.get;

import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuizGetService {

    ResponseDto quizGet(Long quizId);

    QuizGetDto entityToDTO(Long quizId);
}
