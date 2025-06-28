package com.back.kdquiz.user.service;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.exception.userException.UserNotFoundException;
import com.back.kdquiz.quiz.dto.get.QuizAllGetDto;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.MyProfileDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyProfileService {

    private final UsersRepository usersRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> myProfile(CustomUserDetails customUserDetails){

            String email = customUserDetails.getUsername();
            Users users = usersRepository.findByEmail(email);
            if(users==null){
                throw new UserNotFoundException();
            }

            List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
            if(!users.getQuizList().isEmpty()){
                for(Quiz quiz : users.getQuizList()){
                    QuizAllGetDto getDto = QuizAllGetDto.builder()
                                    .id(quiz.getId())
                                    .title(quiz.getTitle())
                                    .updateAt(quiz.getUpdatedAt())
                                    .build();
                    quizAllGetDtoList.add(getDto);
                }
            }

            MyProfileDto myProfileDto = MyProfileDto.builder()
                            .email(users.getEmail())
                            .nickName(users.getNickName())
                            .createAt(users.getCreateAt())
                            .quizList(quizAllGetDtoList)
                            .build();

            return new ResponseEntity<>(ResponseDto.setSuccess("U200", "유저 조회 성공", myProfileDto)
                    , HttpStatus.OK);
    }

}
