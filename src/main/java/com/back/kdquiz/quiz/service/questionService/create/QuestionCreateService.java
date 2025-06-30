package com.back.kdquiz.quiz.service.questionService.create;

import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuestionCreateService {

    ResponseEntity questionCreateResponse(Long quizId);

    QuestionGetDto questionCreateDto(Long quizId);
}
