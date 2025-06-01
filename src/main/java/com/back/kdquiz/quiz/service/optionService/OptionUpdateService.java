package com.back.kdquiz.quiz.service.optionService;

import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.repository.OptionRepository;
import com.back.kdquiz.quiz.dto.update.OptionUpdateDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OptionUpdateService {

    private final OptionRepository optionRepository;

    @Transactional
    public ResponseDto<?> optionUpdate(OptionUpdateDto optionUpdateDto){
        try{
            Optional<Option> optionOptional = optionRepository.findById(optionUpdateDto.getId());
            if(optionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "Option 찾을 수 없습니다.");
            }
            Option option  = optionOptional.get();
            option.setUseCommentary(optionUpdateDto.getUseCommentary());
            option.setCommentary(optionUpdateDto.getCommentary());
            option.setTime(optionUpdateDto.getTime());
            option.setScore(optionUpdateDto.getScore());
            option.setUseAiFeedback(optionUpdateDto.getUseAiFeedBack());
            option.setAiQuestion(optionUpdateDto.getAiQuestion());
            optionRepository.save(option);
            return ResponseDto.setSuccess("Q200", "Option 저장 성공");
        }catch (Exception e){
            log.info("Option 에러 "+e.getMessage());
            return ResponseDto.setFailed("Q001", "Option 저장 오류 발생");
        }
    }
}
