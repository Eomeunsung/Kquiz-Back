package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.service.choiceService.ChoiceCreateService;
import com.back.kdquiz.quiz.service.choiceService.delete.ChoiceDeleteService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/choice")
@AllArgsConstructor
public class ChoiceController {

    private final ChoiceCreateService choiceCreateService;
    private final ChoiceDeleteService choiceDeleteService;

    @GetMapping("/create/{questionId}")
    public ResponseEntity<ResponseDto<?>> choiceCreate(@PathVariable Long questionId){
        ResponseDto responseDto = choiceCreateService.choiceCreate(questionId);
        if(responseDto.getCode().equals("Q200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{choiceId}")
    public ResponseEntity<ResponseDto<?>> choiceDelete(@PathVariable Long choiceId){
        ResponseDto responseDto = choiceDeleteService.deleteChoice(choiceId);
        if(responseDto.getCode().equals("C200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
