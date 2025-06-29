package com.back.kdquiz.quiz.service.questionService;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.service.choiceService.update.ChoiceUpdateService;
import com.back.kdquiz.quiz.service.optionService.update.OptionUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QuestionUpdateService {

    private final QuestionRepository questionRepository;
    private final ChoiceUpdateService choiceUpdateService;
    private final OptionUpdateService optionUpdateService;

    @Transactional
    public ResponseDto<?> questionUpdate(QuestionUpdateDto questionUpdateDto){
        try{
            Optional<Question> questionOptional = questionRepository.findById(questionUpdateDto.getId());
            if(questionOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "Question를 찾을 수 없습니다.");
            }
            Question question = questionOptional.get();
            question.setTitle(questionUpdateDto.getTitle());
            question.setContent(questionUpdateDto.getContent());
            question.setUpdatedAt(LocalDateTime.now());
            questionRepository.save(question);
            for(ChoiceUpdateDto choiceUpdateDto : questionUpdateDto.getChoices()){
                choiceUpdateService.choiceUpdate(choiceUpdateDto);
            }

            optionUpdateService.optionUpdate(questionUpdateDto.getOption());
            return ResponseDto.setSuccess("Q200", "Question 저장 성공", questionUpdateDto);
        }catch (Exception e){
            log.info("questionUpdate 에러 "+e.getMessage());
            return ResponseDto.setFailed("Q001", "Question 저장 오류 발생");
        }
    }
}
