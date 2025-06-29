package com.back.kdquiz.quiz.service.optionService.update;

import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.repository.OptionRepository;
import com.back.kdquiz.exception.optionException.OptionNotFoundException;
import com.back.kdquiz.quiz.dto.update.OptionUpdateDto;
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
public class OptionUpdateService {

    private final OptionRepository optionRepository;

    @Transactional
    public ResponseEntity optionUpdate(OptionUpdateDto optionUpdateDto){
        Optional<Option> optionOptional = optionRepository.findById(optionUpdateDto.getId());
        if(optionOptional.isEmpty()){
            throw new OptionNotFoundException();
        }
        Option option  = optionOptional.get();

        option.setUseCommentary(optionUpdateDto.getUseCommentary());
        option.setCommentary(optionUpdateDto.getCommentary());
        option.setTime(optionUpdateDto.getTime());
        option.setScore(optionUpdateDto.getScore());
        option.setUseAiFeedback(optionUpdateDto.getUseAiFeedBack());
        option.setAiQuestion(optionUpdateDto.getAiQuestion());
        optionRepository.save(option);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Option 저장 성공"));
    }
}
