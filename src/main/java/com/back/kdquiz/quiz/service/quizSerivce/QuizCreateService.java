package com.back.kdquiz.quiz.service.quizSerivce;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.QuizRepository;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.service.questionService.QuestionCreateService;
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

        try {
            Users users = usersRepository.findByEmail(userDetails.getUsername());
            if(users==null){
                return ResponseDto.setFailed("U000", "로그인이 만료되었습니다.");
            }
            Quiz quiz = new Quiz();
            quiz.setTitle(quizCreateDto.getTitle());
            quiz.setCreatedAt(LocalDateTime.now());
            quiz.setUpdatedAt(LocalDateTime.now());
            quiz.setUsers(users);
            quizRepository.save(quiz);
            ResponseDto responseDto = questionCreateService.questionCreate(quiz.getId());
            if(!responseDto.getCode().equals("Q200")){
                return ResponseDto.setFailed("Q000", "Question 생성 실패");
            }
            return ResponseDto.setSuccess("Q200", "Quiz 생성 성공", quiz.getId());
        } catch (Exception e) {
            return ResponseDto.setFailed("Q001", "Quiz 생성 실패");

        }
    }

}
