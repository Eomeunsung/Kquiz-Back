package com.back.kdquiz.quiz.service.questionService.update;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.exception.questionException.QuestionNotFoundException;
import com.back.kdquiz.quiz.dto.update.ChoiceUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.service.choiceService.update.ChoiceUpdateService;
import com.back.kdquiz.quiz.service.optionService.update.OptionUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QuestionUpdateServiceImpl implements QuestionUpdateService{

    private final QuestionRepository questionRepository;
    private final ChoiceUpdateService choiceUpdateService;
    private final OptionUpdateService optionUpdateService;


    @Transactional
    @Override
    public ResponseEntity questionUpdateResponse(QuestionUpdateDto questionUpdateDto) {
        Optional<Question> questionOptional = questionRepository.findById(questionUpdateDto.getId());
        if(questionOptional.isEmpty()){
           throw new QuestionNotFoundException();
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Question 저장 성공", questionUpdateDto));
    }

    @Transactional
    @Override
    public void questionUpdateDto(QuestionUpdateDto questionUpdateDto) {

    }
}
