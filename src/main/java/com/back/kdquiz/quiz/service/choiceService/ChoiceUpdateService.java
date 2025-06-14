package com.back.kdquiz.quiz.service.choiceService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChoiceUpdateService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public ResponseDto<?> choiceUpdate(ChoiceUpdateDto choiceUpdateDto){
        try{
            Optional<Choice> choiceOptional = choiceRepository.findById(choiceUpdateDto.getId());
            if(choiceOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "Choice 저장 실패");
            }
            Choice choice = choiceOptional.get();
            choice.setContent(choiceUpdateDto.getContent());
            choice.setIsCorrect(choiceUpdateDto.getIsCorrect());
            choiceRepository.save(choice);
            return ResponseDto.setSuccess("Q200", "Choice 저장 성공");
        }catch (Exception e){
            log.info("choiceUpdate 에러 "+e.getMessage());
            return ResponseDto.setFailed("Q001", "Choice 저장 오류 발생");
        }
    }
}
