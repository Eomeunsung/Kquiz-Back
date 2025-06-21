package com.back.kdquiz.admin.controller;

import com.back.kdquiz.admin.dto.UserUpdateDto;
import com.back.kdquiz.admin.service.AdminUserGetService;
import com.back.kdquiz.admin.service.AdminUserListService;
import com.back.kdquiz.admin.service.AdminUserUpdateService;
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
    private final AdminUserListService adminUserListService;
    private final AdminUserGetService adminUserGetService;
    private final AdminUserUpdateService adminUserUpdateService;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<?>> userGet(@PathVariable Long userId){
        return adminUserGetService.userGet(userId);
    }

    @PutMapping("/user/update")
    public ResponseEntity<ResponseDto<?>> userUpdate(@RequestBody UserUpdateDto userUpdateDto){
        return adminUserUpdateService.userUpdate(userUpdateDto);
    }

}
