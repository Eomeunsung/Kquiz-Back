package com.back.kdquiz.quiz.service.quizSerivce.update;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import com.back.kdquiz.quiz.service.questionService.update.QuestionUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizUpdateServiceImpl implements QuizUpdateService{

    private final QuizRepository quizRepository;
    private final QuestionUpdateService questionUpdateService;

    @Transactional
    @Override
    public ResponseEntity quizUpdateResponse(QuizUpdateDto quizUpdateDto) {

        Long quizId = quizUpdateDto.getId();
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isEmpty()){
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizOptional.get();
        quiz.setTitle(quizUpdateDto.getTitle());
        quiz.setUpdatedAt(LocalDateTime.now());
        quizRepository.save(quiz);
        for(QuestionUpdateDto questionUpdateDto : quizUpdateDto.getQuestions()){
            questionUpdateService.questionUpdateDto(questionUpdateDto);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Quiz 저장성공"));

    }
}
