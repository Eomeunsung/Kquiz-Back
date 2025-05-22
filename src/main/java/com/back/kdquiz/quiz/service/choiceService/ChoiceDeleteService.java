package com.back.kdquiz.quiz.service.choiceService;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ChoiceDeleteService {

    private final ChoiceRepository choiceRepository;
    @Transactional
    public ResponseDto<?> deleteChoice(Long choiceId){
        try{
            Optional<Choice> choiceOptional = choiceRepository.findById(choiceId);
            if(choiceOptional ==null){
               return ResponseDto.setFailed("C000", "choice가 없습니다.");
            }

            Choice choice = new Choice();
            choiceRepository.delete(choice);
            return ResponseDto.setSuccess("C200", "초이스 삭제 했습니다.");
        }catch (Exception e){
            return ResponseDto.setFailed("C001", "오류가 발행 하였습니다."+e.getMessage());
        }
    }
}
