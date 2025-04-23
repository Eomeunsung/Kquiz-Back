package com.back.kdquiz.quiz.service.optionService;

import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.repository.OptionRepository;
import com.back.kdquiz.quiz.dto.OptionGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OptionGetService {

    private final OptionRepository optionRepository;

    @Transactional
    public ResponseDto<?> optionGet(Long id){

        try{
            Optional<Option> optionalOption = optionRepository.findById(id);
            if(optionalOption.isEmpty()){
                return ResponseDto.setFailed("Q000", "Option 이 없습니다.");
            }
            Option option = optionalOption.get();
            OptionGetDto optionGetDto = new OptionGetDto();
            optionGetDto.setId(option.getId());
            optionGetDto.setTime(option.getTime());
            optionGetDto.setScore(option.getScore());
            optionGetDto.setUseAiFeedBack(option.getUseAiFeedback());
            optionGetDto.setAiQuestion(option.getAiQuestion());
            optionGetDto.setCommentary(option.getCommentary());
            return ResponseDto.setSuccess("Q200", "Option 조회 성공", optionGetDto);
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "오류 발생");
        }

    }

}
