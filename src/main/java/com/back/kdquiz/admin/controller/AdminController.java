package com.back.kdquiz.admin.controller;

import com.back.kdquiz.quiz.service.quizSerivce.QuizDeleteService;
import com.back.kdquiz.quiz.service.quizSerivce.QuizListService;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final QuizListService quizListService;
    private final QuizDeleteService quizDeleteService;

    @GetMapping("/quiz/list")
    public ResponseEntity<ResponseDto<?>> quizList(){
        return quizListService.quizAllList();
    }

    @DeleteMapping("/quiz/delete/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizDelete(@PathVariable Long quizId){
        return quizDeleteService.quizDelete(quizId);
    }

}
