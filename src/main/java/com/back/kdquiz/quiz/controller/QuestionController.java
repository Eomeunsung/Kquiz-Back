package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.dto.update.QuestionUpdateDto;
import com.back.kdquiz.quiz.service.questionService.create.QuestionCreateService;
import com.back.kdquiz.quiz.service.questionService.delete.QuestionDeleteService;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetIdService;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetService;
import com.back.kdquiz.quiz.service.questionService.update.QuestionUpdateService;
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
    private final QuestionGetIdService questionGetIdService;

    @GetMapping("/create/{quizId}")
    public ResponseEntity<ResponseDto<?>> questionCreate(@PathVariable Long quizId){
        return questionCreateService.questionCreateResponse(quizId);
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<ResponseDto<?>> questionDelete(@PathVariable Long questionId){
        return questionDeleteService.questionDeleteResponse(questionId);
    }

    @GetMapping("/get/{questionId}")
    public ResponseEntity<ResponseDto<?>> questionGet(@PathVariable Long questionId){
        return questionGetService.questionGetResponse(questionId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<?>> questionUpdate(@RequestBody QuestionUpdateDto questionUpdateDto){
        return questionUpdateService.questionUpdateResponse(questionUpdateDto);
    }

    //일단 안쓰는거
    @GetMapping("/get/questionId/{quizId}")
    public ResponseEntity<ResponseDto<?>> questionGetId(@PathVariable Long quizId){
        ResponseDto responseDto = questionGetIdService.questionGetId(quizId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
