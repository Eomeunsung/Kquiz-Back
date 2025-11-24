package com.back.kdquiz.quiz.service.questionService.get;

import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.repository.QuestionRepository;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.questionException.QuestionNotFoundException;
import com.back.kdquiz.quiz.dto.get.ChoiceGetDto;
import com.back.kdquiz.quiz.dto.get.OptionGetDto;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.choiceService.get.ChoiceGetService;
import com.back.kdquiz.quiz.service.optionService.get.OptionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class QuestionGetServiceImpl implements QuestionGetService{

    private final QuestionRepository questionRepository;
    private final ChoiceGetService choiceGetService;
    private final OptionGetService optionGetService;
    private final QuizRepository quizRepository;

    @Transactional
    @Override
    public ResponseEntity questionGetResponse(Long questionId) {

        QuestionGetDto questionGetDto = questionGetDto(questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "question 목록 조회 성공", questionGetDto));
    }

    @Transactional
    @Override
    public QuestionGetDto questionGetDto(Long questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isEmpty()){
            throw new QuestionNotFoundException();
        }
        Question question = questionOptional.get();

        List<ChoiceGetDto> choiceGetDtoList = choiceGetService.choiceGetDto(questionId);

        OptionGetDto optionGetDto = optionGetService.optionGetDto(questionId);

       return QuestionGetDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .updateAt(question.getUpdatedAt())
                .choices(choiceGetDtoList)
                .option(optionGetDto)
                .build();
    }

    @Transactional
    @Override
    public QuestionGetDto questionGetDto(Question question) {
        List<ChoiceGetDto> choiceGetDtoList = choiceGetService.choiceGetDto(question.getChoices());
        OptionGetDto optionGetDto = optionGetService.optionGetDto(question.getOption());

        return QuestionGetDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .updateAt(question.getUpdatedAt())
                .choices(choiceGetDtoList)
                .option(optionGetDto)
                .build();
    }



}
