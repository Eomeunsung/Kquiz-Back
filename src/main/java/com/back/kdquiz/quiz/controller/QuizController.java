package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.quiz.dto.create.QuizCreateDto;
import com.back.kdquiz.quiz.dto.update.QuizTitleUpdateDto;
import com.back.kdquiz.quiz.dto.update.QuizUpdateDto;
import com.back.kdquiz.quiz.service.quizSerivce.*;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizCreateService quizCreateService;
    private final QuizGetService quizGetService;
    private final QuizListService quizListService;
    private final QuizUpdateService quizUpdateService;
    private final QuizDeleteService quizDeleteService;
    private final QuizTitleUpdateService quizTitleUpdateService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<?>> quizCreate(@RequestBody QuizCreateDto quizCreateDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        ResponseDto responseDto = quizCreateService.quizCreate(quizCreateDto, userDetails);

        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<?>> quizListGet(){
        return quizListService.quizAllList();
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizGet(@PathVariable Long quizId){
        ResponseDto responseDto = quizGetService.quizGet(quizId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<?>> quizUpdate(@RequestBody QuizUpdateDto quizUpdateDto){
        ResponseDto responseDto = quizUpdateService.quizUpdate(quizUpdateDto);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/title")
    public ResponseEntity<ResponseDto<?>> quizTitleUpdate(@RequestBody QuizTitleUpdateDto quizTitleUpdateDto){
        return quizTitleUpdateService.quizTitleUpdate(quizTitleUpdateDto);
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizDelete(@PathVariable Long quizId){
        return quizDeleteService.quizDelete(quizId);
    }


}
