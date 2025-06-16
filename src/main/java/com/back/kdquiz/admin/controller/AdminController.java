package com.back.kdquiz.admin.controller;

import com.back.kdquiz.admin.service.AdminUserListService;
import com.back.kdquiz.quiz.service.quizSerivce.QuizDeleteService;
import com.back.kdquiz.quiz.service.quizSerivce.QuizListService;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final QuizListService quizListService;
    private final QuizDeleteService quizDeleteService;
    private final AdminUserListService adminUserListService;

    public AdminController(QuizListService quizListService, QuizDeleteService quizDeleteService, AdminUserListService adminUserListService) {
        this.quizListService = quizListService;
        this.quizDeleteService = quizDeleteService;
        this.adminUserListService = adminUserListService;
    }

    @GetMapping("/quiz/list")
    public ResponseEntity<ResponseDto<?>> quizList(){
        return quizListService.quizAllList();
    }

    @DeleteMapping("/quiz/delete/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizDelete(@PathVariable Long quizId){
        return quizDeleteService.quizDelete(quizId);
    }

    @GetMapping("/user/list")
    public ResponseEntity<ResponseDto<?>> userList(){
        return adminUserListService.userList();
    }

}
