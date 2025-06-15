package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizListService {

    private final QuizRepository quizRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> quizAllList(){
        ResponseDto responseDto;
        try{
            List<Quiz> quizList = quizRepository.findAll();
            if(quizList.isEmpty()){
                responseDto = ResponseDto.setFailed("Q000", "퀴즈가 없음");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            List<QuizAllGetDto> quizAllGetDtoList = buildQuizAllListResponse(quizList);
            responseDto =  ResponseDto.setSuccess("Q200", "퀴즈 목록 조회 성공", quizAllGetDtoList);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e){
            responseDto = ResponseDto.setFailed("Q000", "퀴즈 조회 오류"+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    private List<QuizAllGetDto> buildQuizAllListResponse(List<Quiz> quizList){
        List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
        for(Quiz quiz : quizList){
            QuizAllGetDto qad = new QuizAllGetDto();
            qad.setId(quiz.getId());
            qad.setTitle(quiz.getTitle());
            qad.setUpdateAt(quiz.getUpdatedAt());
            quizAllGetDtoList.add(qad);
        }
        return quizAllGetDtoList;
    }
}
