package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.dto.QuizCreateDto;
import com.back.kdquiz.quiz.service.quizSerivce.QuizCreateService;
import com.back.kdquiz.quiz.service.quizSerivce.QuizGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizCreateService quizCreateService;
    private final QuizGetService quizGetService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<?>> quizCreate(@RequestBody QuizCreateDto quizCreateDto){
        ResponseDto responseDto = quizCreateService.quizCreate(quizCreateDto);

        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/quiz/list")


    @GetMapping("/get/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizGet(@PathVariable Long quizId){
        ResponseDto responseDto = quizGetService.quizGet(quizId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
