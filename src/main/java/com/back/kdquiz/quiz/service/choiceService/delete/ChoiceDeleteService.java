package com.back.kdquiz.quiz.service.choiceService.delete;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.exception.choiceException.ChoiceNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ChoiceDeleteService {

    private final ChoiceRepository choiceRepository;
    @Transactional
    public ResponseEntity deleteChoice(Long choiceId){

            Optional<Choice> choiceOptional = choiceRepository.findById(choiceId);
            if(choiceOptional == null){
               throw new ChoiceNotFoundException();
            }
            Choice choice = new Choice();
            choiceRepository.delete(choice);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.setSuccess("C200", "초이스 삭제 했습니다."));

    }
}
