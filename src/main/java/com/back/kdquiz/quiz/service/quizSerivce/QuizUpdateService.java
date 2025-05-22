package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import com.back.kdquiz.quiz.service.questionService.QuestionUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizUpdateService {

    private final QuizRepository quizRepository;
    private final QuestionUpdateService questionUpdateService;

    @Transactional
    public ResponseDto<?> quizUpdate(QuizUpdateDto quizUpdateDto){
        try{
            Long quizId = quizUpdateDto.getId();
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if(quizOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "퀴즈를 찾을 수 없습니다.");
            }
            Quiz quiz = quizOptional.get();
            quiz.setTitle(quizUpdateDto.getTitle());
            quiz.setUpdatedAt(LocalDateTime.now());
            quizRepository.save(quiz);
            for(QuestionUpdateDto questionUpdateDto : quizUpdateDto.getQuestions()){
                questionUpdateService.questionUpdate(questionUpdateDto);
            }
            return ResponseDto.setSuccess("Q200", "Quiz 저장성공");
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "Quiz 저장 오류 발생");
        }
    }
}
