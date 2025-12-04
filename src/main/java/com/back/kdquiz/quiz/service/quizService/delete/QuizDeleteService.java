package com.back.kdquiz.quiz.service.quizService.delete;

import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface QuizDeleteService {

    ResponseDto quizDelete(Long quizId) throws IOException;


}
