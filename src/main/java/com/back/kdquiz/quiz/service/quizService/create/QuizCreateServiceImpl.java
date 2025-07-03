package com.back.kdquiz.quiz.service.quizService.create;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.exception.userException.UserNotFoundException;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.service.questionService.create.QuestionCreateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class QuizCreateServiceImpl implements QuizCreateService{

    private final UsersRepository usersRepository;
    private final QuizRepository quizRepository;
    private final QuestionCreateService questionCreateService;

    @Transactional
    @Override
    public ResponseEntity quizCreateResponse(QuizCreateDto quizCreateDto, CustomUserDetails userDetails) {
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
        questionCreateService.questionCreateDto(quiz.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("Q200", "Quiz 생성 성공", quiz.getId()));
    }

    @Transactional
    @Override
    public long quizCreateDto(QuizCreateDto quizCreateDto) {
        return 0;
    }
}
