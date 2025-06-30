package com.back.kdquiz.quiz.service.questionService.get;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuestionGetService {
    ResponseEntity<ResponseDto<?>> questionGetResponse(Long questionId);

    QuestionGetDto questionGetDto(Long questionId);

    QuestionGetDto questionGetDto(Question question);
}
