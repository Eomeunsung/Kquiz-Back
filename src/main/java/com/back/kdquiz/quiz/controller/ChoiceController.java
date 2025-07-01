package com.back.kdquiz.quiz.controller;

import com.back.kdquiz.quiz.service.choiceService.create.ChoiceCreateService;
import com.back.kdquiz.quiz.service.choiceService.delete.ChoiceDeleteService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
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
        return choiceCreateService.choiceCreateResponse(questionId);
    }

    @DeleteMapping("/delete/{choiceId}")
    public ResponseEntity<ResponseDto<?>> choiceDelete(@PathVariable Long choiceId){
        return choiceDeleteService.deleteChoiceResponse(choiceId);
    }
}
