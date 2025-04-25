package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.get.ChoiceGetDto;
import com.back.kdquiz.quiz.dto.get.OptionGetDto;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.choiceService.ChoiceGetService;
import com.back.kdquiz.quiz.service.optionService.OptionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class QuestionGetService {

    private final QuestionRepository questionRepository;
    private final ChoiceGetService choiceGetService;
    private final OptionGetService optionGetService;

    @Transactional
    public ResponseDto<?> questionGet(Long id){
        try{
            Optional<Question> questionOptional = questionRepository.findById(id);
            if(questionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000","question 없습니다.");
            }
            Question question = questionOptional.get();


            ResponseDto choiceResponseDto = choiceGetService.choiceGet(question.getId());
            if(!"Q200".equals(choiceResponseDto.getCode())){
                return ResponseDto.setFailed("Q001", "Choice 조회 오류");
            }
            List<ChoiceGetDto> choiceGetDtoList = (List<ChoiceGetDto>) choiceResponseDto.getData();


            ResponseDto optionGetDtoResponseDto = optionGetService.optionGet(question.getId());
            if(!"Q200".equals(optionGetDtoResponseDto.getCode())){
                return ResponseDto.setFailed("Q002", "Option 조회 오류");
            }
            OptionGetDto optionGetDto = (OptionGetDto) optionGetDtoResponseDto.getData();


            QuestionGetDto questionGetDto = QuestionGetDto.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .updateAt(question.getUpdatedAt())
                    .choices(choiceGetDtoList)
                    .option(optionGetDto)
                    .build();
            return ResponseDto.setSuccess("Q200", "question 목록 조회 성공", questionGetDto);
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "question 오류 발생: " + e.getMessage());
        }
    }
}
