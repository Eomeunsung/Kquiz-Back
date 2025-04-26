package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.service.questionService.QuestionCreateService;
import com.back.kdquiz.quiz.service.questionService.QuestionDeleteService;
import com.back.kdquiz.quiz.service.questionService.QuestionGetService;
import com.back.kdquiz.quiz.service.questionService.QuestionUpdateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {

    private final QuestionCreateService questionCreateService;
    private final QuestionDeleteService questionDeleteService;
    private final QuestionGetService questionGetService;
    private final QuestionUpdateService questionUpdateService;

    @GetMapping("/create/{quizId}")
    public ResponseEntity<ResponseDto<?>> questionCreate(@PathVariable Long quizId){
        ResponseDto responseDto = questionCreateService.questionCreate(quizId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<ResponseDto<?>> questionDelete(@PathVariable Long questionId){
        ResponseDto responseDto = questionDeleteService.questionDelete(questionId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{questionId}")
    public ResponseEntity<ResponseDto<?>> questionGet(@PathVariable Long questionId){
        ResponseDto responseDto = questionGetService.questionGet(questionId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<?>> questionUpdate(@RequestBody QuestionUpdateDto questionUpdateDto){
        ResponseDto responseDto = questionUpdateService.questionUpdate(questionUpdateDto);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
