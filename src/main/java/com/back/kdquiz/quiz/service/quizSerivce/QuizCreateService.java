package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.QuizCreateDto;
import com.back.kdquiz.quiz.service.questionService.QuestionCreateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class QuizCreateService {

    private final QuizRepository quizRepository;
    private final QuestionCreateService questionCreateService;

    @Transactional
    public ResponseDto<?> quizCreate(QuizCreateDto quizCreateDto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateDto.getTitle());
        quiz.setCreatedAt(LocalDateTime.now());
        try {
            quizRepository.save(quiz);
            ResponseDto responseDto = questionCreateService.questionCreate(quiz.getId());
            if(!responseDto.getCode().equals("Q200")){
                return ResponseDto.setFailed("Q000", "Question 생성 실패");
            }
            return ResponseDto.setSuccess("Q200", "Quiz 생성 성공", null);
        } catch (Exception e) {
            return ResponseDto.setFailed("Q001", "Quiz 생성 실패");

        }
    }

}
