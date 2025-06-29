package com.back.kdquiz.quiz.service.choiceService.get;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.exception.choiceException.ChoiceNotFoundException;
import com.back.kdquiz.quiz.dto.get.ChoiceGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChoiceGetServiceImpl implements ChoiceGetService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    @Override
    public ResponseEntity choiceGetResponse(Long questionId) {
        List<ChoiceGetDto> choiceGetDtoList = choiceGetDTO(questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "question 조회 성공", choiceGetDtoList));
    }

    @Transactional
    @Override
    public List<ChoiceGetDto> choiceGetDTO(Long questionId) {
        List<Choice> choiceList = choiceRepository.findByQuestion_Id(questionId);
        if(choiceList.isEmpty()){
            return new ArrayList<>();
        }
        List<ChoiceGetDto> choiceGetDtoList = new ArrayList<>();
        for(Choice choice : choiceList){
            ChoiceGetDto choiceGetDto = new ChoiceGetDto();
            choiceGetDto.setId(choice.getId());
            choiceGetDto.setContent(choice.getContent());
            choiceGetDto.setIsCorrect(choice.getIsCorrect());
            choiceGetDtoList.add(choiceGetDto);
        }
        return choiceGetDtoList;
    }
}
