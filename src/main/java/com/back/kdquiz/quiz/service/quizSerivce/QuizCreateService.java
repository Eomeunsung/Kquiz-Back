package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.exception.quizException.QuizCreateFailedException;
import com.back.kdquiz.exception.userException.UserNotFoundException;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.service.questionService.create.QuestionCreateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class QuizCreateService {

    private final UsersRepository usersRepository;
    private final QuizRepository quizRepository;
    private final QuestionCreateService questionCreateService;

    @Transactional
    public ResponseDto<?> quizCreate(QuizCreateDto quizCreateDto, CustomUserDetails userDetails) {

        Users users = usersRepository.findByEmail(userDetails.getUsername());
        if(users==null){
            throw new UserNotFoundException();
        }
        Quiz quiz = Quiz.builder()
                .title(quizCreateDto.getTitle())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .users(users)
                .build();
        quizRepository.save(quiz);
        ResponseDto responseDto = questionCreateService.questionCreate(quiz.getId());
        if(!responseDto.getCode().equals("Q200")){
            throw new QuizCreateFailedException();
        }
        return ResponseDto.setSuccess("Q200", "Quiz 생성 성공", quiz.getId());

    }

}
