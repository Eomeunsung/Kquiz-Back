package com.back.kdquiz.quiz.service.choiceService.delete;

import org.springframework.http.ResponseEntity;

public interface ChoiceDeleteService {
    ResponseEntity deleteChoiceResponse(Long choiceId);
}
