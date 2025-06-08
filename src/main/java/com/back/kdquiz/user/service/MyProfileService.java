package com.back.kdquiz.user.service;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.domain.entity.Quiz;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
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
        ResponseDto responseDto;
        try{
            String email = customUserDetails.getUsername();
            Users users = usersRepository.findByEmail(email);
            if(users==null){
                responseDto = ResponseDto.setFailed("U000", "유저를 찾을 수 없습니다.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            MyProfileDto myProfileDto = new MyProfileDto();
            myProfileDto.setEmail(users.getEmail());
            myProfileDto.setNickName(users.getNickName());
            myProfileDto.setCreateAt(users.getCreateAt());

            if(!users.getQuizList().isEmpty()){
                List<QuizAllGetDto> quizAllGetDtoList = new ArrayList<>();
                for(Quiz quiz : users.getQuizList()){
                    QuizAllGetDto getDto = new QuizAllGetDto();
                    getDto.setId(quiz.getId());
                    getDto.setTitle(quiz.getTitle());
                    getDto.setUpdateAt(quiz.getUpdatedAt());
                    quizAllGetDtoList.add(getDto);
                }
                myProfileDto.setQuizList(quizAllGetDtoList);
            }
            responseDto = ResponseDto.setSuccess("U200", "유저 조회 성공", myProfileDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("U000", "오류 발생하였습니다. "+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

}
