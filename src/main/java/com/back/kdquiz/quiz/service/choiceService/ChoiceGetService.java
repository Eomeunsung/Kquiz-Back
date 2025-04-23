package com.back.kdquiz.quiz.service.choiceService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.quiz.dto.ChoiceGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChoiceGetService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public ResponseDto<?> choiceGet(Long questionId){
        try{
            List<Choice> choiceList = choiceRepository.findByQuestion_Id(questionId);
            if(choiceList.isEmpty()){
                return ResponseDto.setFailed("Q000", "question 없습니다.");
            }

            List<ChoiceGetDto> choiceGetDtoList = new ArrayList<>();
            for(Choice choice : choiceList){
                ChoiceGetDto choiceGetDto = new ChoiceGetDto();
                choiceGetDto.setId(choice.getId());
                choiceGetDto.setContent(choice.getContent());
                choiceGetDto.setIsCorrect(choice.getIsCorrect());
                choiceGetDtoList.add(choiceGetDto);
            }
            return ResponseDto.setSuccess("Q200", "question 조회 성공", choiceGetDtoList);
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "알 수 없는 오류 발생");
        }
    }
}
