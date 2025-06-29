package com.back.kdquiz.quiz.service.choiceService.create;

import com.back.kdquiz.quiz.dto.create.ChoiceCreateDto;
import org.springframework.http.ResponseEntity;

public interface ChoiceCreateService {

    ResponseEntity choiceCreateResponse(Long questionId);

    ChoiceCreateDto choiceCreateDto(Long questionId);


}
