package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizListService {

    private final QuizRepository quizRepository;

    @Transactional
    public ResponseDto<?> quizAllList(){
        try{
            List<Quiz> quizList = quizRepository.findAll();
            if(quizList.isEmpty()){
                return ResponseDto.setFailed("Q000", "퀴즈가 없음");
            }
            return buildQuizAllListResponse(quizList);
        } catch (Exception e){
            return ResponseDto.setFailed("Q000", "퀴즈 목록 조회 실패");
        }
    }

    private ResponseDto<?> buildQuizAllListResponse(List<Quiz> quizList){
        List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
        for(Quiz quiz : quizList){
            QuizAllGetDto qad = new QuizAllGetDto();
            qad.setId(quiz.getId());
            qad.setTitle(quiz.getTitle());
            qad.setUpdateAt(quiz.getUpdatedAt());
            quizAllGetDtoList.add(qad);
        }
        return ResponseDto.setSuccess("Q200", "퀴즈 목록 조회 성공", quizAllGetDtoList);
    }
}
