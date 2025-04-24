package com.back.kdquiz.quiz.service.quizSerivce;


import com.back.kdquiz.domain.entity.Question;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import com.back.kdquiz.quiz.service.questionService.QuestionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizGetService {

    private final QuizRepository quizRepository;
    private final QuestionGetService questionGetService;

    @Transactional
    public ResponseDto<?> quizGet(Long id){
        try{
            Optional<Quiz> quizOptional = quizRepository.findById(id);
            if(quizOptional.isEmpty()){
                return ResponseDto.setFailed("Q000", "퀴즈 조회 실패");
            }
            Quiz quiz = quizOptional.get();

            QuizGetDto quizGetDto = new QuizGetDto();
            quizGetDto.setId(quiz.getId());
            quizGetDto.setTitle(quiz.getTitle());
            List<QuestionGetDto> questionGetDtoList = new ArrayList<>();

            for(Question question : quiz.getQuestions()){
                ResponseDto responseDto = questionGetService.questionGet(question.getId());
                if(responseDto.getCode().equals("Q200")){
                    QuestionGetDto questionGetDto = (QuestionGetDto) responseDto.getData();
                    questionGetDtoList.add(questionGetDto);
                }else{
                    return responseDto;
                }
            }
            quizGetDto.setQuestions(questionGetDtoList);
            return ResponseDto.setSuccess("Q200", "퀴즈 목록 조회 성공", quizGetDto);
        }catch (Exception e){
            return ResponseDto.setFailed("Q001", "퀴즈 조회 오류 발생");
        }
    }
}
