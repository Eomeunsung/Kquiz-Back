package com.back.kdquiz.quiz.service.choiceService.update;

import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import org.springframework.http.ResponseEntity;

public interface ChoiceUpdateService {

    ResponseEntity choiceUpdateResponse(ChoiceUpdateDto choiceUpdateDto);

    Boolean choiceUpdateDto(ChoiceUpdateDto choiceUpdateDto);
}
