package com.back.kdquiz.quiz.service.quizService.get;


import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizGetServiceImpl implements QuizGetService {

    private final QuizRepository quizRepository;
    private final QuestionGetService questionGetService;

    @Transactional
    @Override
    public ResponseEntity quizGetResponse(Long quizId) {

        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizOptional.get();

        QuizGetDto quizGetDto = new QuizGetDto();
        quizGetDto.setId(quiz.getId());
        quizGetDto.setTitle(quiz.getTitle());
        List<QuestionGetDto> questionGetDtoList = new ArrayList<>();

        for(Question question : quiz.getQuestions()){
            QuestionGetDto questionGetDto = questionGetService.questionGetDto(question);
            questionGetDtoList.add(questionGetDto);
        }

        quizGetDto.setQuestions(questionGetDtoList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "퀴즈 목록 조회 성공", quizGetDto));

    }

    @Override
    public QuizGetDto quizGetDto(Long quizId) {

        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizOptional.get();

        QuizGetDto quizGetDto = new QuizGetDto();
        quizGetDto.setId(quiz.getId());
        quizGetDto.setTitle(quiz.getTitle());
        List<QuestionGetDto> questionGetDtoList = new ArrayList<>();

        for(Question question : quiz.getQuestions()){
            QuestionGetDto questionGetDto = questionGetService.questionGetDto(question);
            questionGetDtoList.add(questionGetDto);
        }

        quizGetDto.setQuestions(questionGetDtoList);
        return quizGetDto;
    }
}
