package com.back.kdquiz.admin.controller;

import com.back.kdquiz.admin.dto.ResourceCreateDto;
import com.back.kdquiz.admin.dto.RoleCreateDto;
import com.back.kdquiz.admin.dto.UserUpdateDto;
import com.back.kdquiz.admin.service.*;
import com.back.kdquiz.quiz.service.quizService.delete.QuizDeleteService;
import com.back.kdquiz.quiz.service.quizService.QuizListService;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final QuizListService quizListService;
    private final QuizDeleteService quizDeleteService;
    private final AdminUserListService adminUserListService;
    private final AdminUserGetService adminUserGetService;
    private final AdminUserUpdateService adminUserUpdateService;
    private final AdminRoleGetService adminRoleGetService;
    private final AdminResourceGetService adminResourceGetService;
    private final AdminMappingGetService adminMappingGetService;
    private final AdminResourceCreateService adminResourceCreateService;
    private final AdminRoleCreateService adminRoleCreateService;

    @GetMapping("/quiz/list")
    public ResponseEntity<ResponseDto<?>> quizList(){
        return quizListService.quizAllList();
    }

    @DeleteMapping("/quiz/delete/{quizId}")
    public ResponseEntity<ResponseDto<?>> quizDelete(@PathVariable Long quizId) throws IOException {
        return quizDeleteService.quizDeleteResponse(quizId);
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

    @GetMapping("/role/list")
    public ResponseEntity<ResponseDto<?>> roleGet(){
        return adminRoleGetService.roleGet();
    }

    @GetMapping("/resource/list")
    public ResponseEntity<ResponseDto<?>> resourceGet(){
        return adminResourceGetService.resourceGet();
    }

    //ResourceRole-Mapping
    @GetMapping("/mapping/list")
    public ResponseEntity<ResponseDto<?>> mappingGet(){
        return adminMappingGetService.mappingGet();
    }

    @PostMapping("/resource/create")
    public ResponseEntity<ResponseDto<?>> resourceCreate(@RequestBody ResourceCreateDto resourceCreateDto){
        return adminResourceCreateService.resourceCreate(resourceCreateDto);
    }

    @PostMapping("/role/create")
    public ResponseEntity<ResponseDto<?>> roleCreate(@RequestBody RoleCreateDto roleCreateDto){
        return adminRoleCreateService.roleCreate(roleCreateDto);
    }

}
