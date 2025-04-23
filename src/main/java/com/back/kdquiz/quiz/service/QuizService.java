package com.back.kdquiz.quiz.service;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.QuizCreateDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional
    public ResponseDto<?> quizCreate(QuizCreateDto quizCreateDto) {
        try {
            Quiz quiz = new Quiz();
            quiz.setTitle(quizCreateDto.getTitle());
            quizRepository.save(quiz);
            return ResponseDto.setSuccess("Q200", "Quiz 생성 성공", null);
        } catch (Exception e) {
            return ResponseDto.setFailed("Q000", "Quiz 생성 실패");

        }
    }
}
