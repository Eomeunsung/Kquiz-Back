package com.back.kdquiz.quiz.service.choiceService.get;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.quiz.dto.get.ChoiceGetDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChoiceGetService {

    ResponseEntity choiceGetResponse(Long questionId);

    List<ChoiceGetDto> choiceGetDto(Long questionId);

    List<ChoiceGetDto> choiceGetDto(List<Choice> choiceList);
}
