package com.back.kdquiz.quiz.service.questionService.update;

import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import org.springframework.http.ResponseEntity;

public interface QuestionUpdateService {
    ResponseEntity questionUpdateResponse(QuestionUpdateDto questionUpdateDto);

    Boolean questionUpdateDto(QuestionUpdateDto questionUpdateDto);
}
