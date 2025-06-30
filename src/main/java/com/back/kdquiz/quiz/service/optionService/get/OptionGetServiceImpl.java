package com.back.kdquiz.quiz.service.optionService.get;

import com.back.kdquiz.domain.entity.Option;
import com.back.kdquiz.domain.repository.OptionRepository;
import com.back.kdquiz.exception.optionException.OptionNotFoundException;
import com.back.kdquiz.quiz.dto.get.OptionGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OptionGetServiceImpl implements OptionGetService {

    private final OptionRepository optionRepository;

    @Transactional
    @Override
    public ResponseEntity optionGetResponse(Long optionId) {
            Optional<Option> optionalOption = optionRepository.findById(optionId);
            if(optionalOption.isEmpty()){
                throw new OptionNotFoundException();
            }
            Option option = optionalOption.get();
            OptionGetDto optionGetDto = OptionGetDto.builder()
                    .id(option.getId())
                    .time(option.getTime())
                    .score(option.getScore())
                    .useAiFeedBack(option.getUseAiFeedback())
                    .aiQuestion(option.getAiQuestion())
                    .useCommentary(option.getUseCommentary())
                    .commentary(option.getCommentary())
                    .build();

            return ResponseEntity.
                    status(HttpStatus.OK)
                    .body(ResponseDto.setSuccess("O200", "Option 조회 성공", optionGetDto));

    }

    @Transactional
    @Override
    public OptionGetDto optionGetDto(Long optionId) {
        Optional<Option> optionalOption = optionRepository.findById(optionId);
        if(optionalOption.isEmpty()){
            throw new OptionNotFoundException();
        }
        Option option = optionalOption.get();

        return OptionGetDto.builder()
                .id(option.getId())
                .time(option.getTime())
                .score(option.getScore())
                .useAiFeedBack(option.getUseAiFeedback())
                .aiQuestion(option.getAiQuestion())
                .useCommentary(option.getUseCommentary())
                .commentary(option.getCommentary())
                .build();
    }

    @Override
    public OptionGetDto optionGetDto(Option option) {
        return OptionGetDto.builder()
                .id(option.getId())
                .time(option.getTime())
                .score(option.getScore())
                .useAiFeedBack(option.getUseAiFeedback())
                .aiQuestion(option.getAiQuestion())
                .useCommentary(option.getUseCommentary())
                .commentary(option.getCommentary())
                .build();
    }
}
