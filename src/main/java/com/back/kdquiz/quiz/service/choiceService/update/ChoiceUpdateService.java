package com.back.kdquiz.quiz.service.choiceService.update;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.exception.choiceException.ChoiceNotFoundException;
import com.back.kdquiz.exception.choiceException.ChoiceSaveFailedException;
import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChoiceUpdateService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public ResponseEntity choiceUpdate(ChoiceUpdateDto choiceUpdateDto){
        Optional<Choice> choiceOptional = choiceRepository.findById(choiceUpdateDto.getId());
        if(choiceOptional.isEmpty()){
            throw new ChoiceNotFoundException();
        }
        Choice choice = choiceOptional.get();
        choice.setContent(choiceUpdateDto.getContent());
        choice.setIsCorrect(choiceUpdateDto.getIsCorrect());
        choiceRepository.save(choice);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Choice 저장 성공"));
    }
}
