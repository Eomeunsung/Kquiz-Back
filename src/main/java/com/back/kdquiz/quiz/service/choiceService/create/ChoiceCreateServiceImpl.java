package com.back.kdquiz.quiz.service.choiceService.create;

import com.back.kdquiz.domain.entity.Choice;
import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.ChoiceRepository;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.exception.questionException.QuestionNotFoundException;
import com.back.kdquiz.quiz.dto.create.ChoiceCreateDto;
import com.back.kdquiz.quiz.init.QuizInit;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChoiceCreateServiceImpl implements ChoiceCreateService{

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuizInit quizInit;

    @Transactional
    @Override
    public ResponseEntity choiceCreateResponse(Long questionId) {
            ChoiceCreateDto choiceCreateDto = choiceCreateDto(questionId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.setSuccess("Q200", "Choice 생성 성공", choiceCreateDto));
    }

    @Transactional
    @Override
    public ChoiceCreateDto choiceCreateDto(Long questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isEmpty()){
            throw new QuestionNotFoundException();
        }
        Question question = questionOptional.get();
        Choice choice = quizInit.choiceInit(new Choice());
        choice.setQuestion(question);
        choiceRepository.save(choice);

       return ChoiceCreateDto
                .builder()
                .id(choice.getId())
                .content(choice.getContent())
                .isCorrect(choice.getIsCorrect())
                .build();
    }
}
