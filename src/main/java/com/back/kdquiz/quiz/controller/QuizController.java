package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.dto.QuizCreateDto;
import com.back.kdquiz.quiz.service.QuizService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.RemoteRef;

@RestController
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz/create")
    public ResponseEntity<ResponseDto<?>> quizCreate(@RequestBody QuizCreateDto quizCreateDto){
        ResponseDto responseDto = quizService.quizCreate(quizCreateDto);

        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/quiz/list")


//    @GetMapping("/quiz/{id}")
}
