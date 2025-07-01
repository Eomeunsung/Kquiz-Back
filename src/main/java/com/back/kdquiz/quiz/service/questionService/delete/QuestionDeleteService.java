package com.back.kdquiz.quiz.service.questionService.delete;

import org.springframework.http.ResponseEntity;

public interface QuestionDeleteService {

    ResponseEntity questionDeleteResponse(Long questionId);


}
