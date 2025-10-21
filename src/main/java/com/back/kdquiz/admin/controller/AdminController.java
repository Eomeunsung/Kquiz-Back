package com.back.kdquiz.admin.controller;

import com.back.kdquiz.admin.dto.MappingUpdateDto;
import com.back.kdquiz.admin.dto.resourceDto.ResourceCreateDto;
import com.back.kdquiz.admin.dto.resourceDto.ResourceUpdateDto;
import com.back.kdquiz.admin.dto.roleDto.RoleCreateDto;
import com.back.kdquiz.admin.dto.UserUpdateDto;
import com.back.kdquiz.admin.dto.roleDto.RoleUpdateDto;
import com.back.kdquiz.admin.service.*;
import com.back.kdquiz.admin.service.resourceService.ResourceCreateService;
import com.back.kdquiz.admin.service.resourceService.ResourceDeleteService;
import com.back.kdquiz.admin.service.resourceService.ResourceGetService;
import com.back.kdquiz.admin.service.resourceService.ResourceUpdateService;
import com.back.kdquiz.admin.service.roleService.RoleCreateService;
import com.back.kdquiz.admin.service.roleService.RoleDeleteService;
import com.back.kdquiz.admin.service.roleService.RoleGetService;
import com.back.kdquiz.admin.service.roleService.RoleUpdateService;
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
    private final UserListService adminUserListService;
    private final UserGetService adminUserGetService;
    private final UserUpdateService userUpdateService;
    private final RoleGetService roleGetService;
    private final ResourceGetService resourceGetService;
    private final MappingGetService mappingGetService;
    private final ResourceCreateService resourceCreateService;
    private final RoleCreateService roleCreateService;
    private final MappingUpdateService mappingUpdateService;
    private final RoleUpdateService roleUpdateService;
    private final RoleDeleteService roleDeleteService;
    private final ResourceDeleteService resourceDeleteService;
    private final ResourceUpdateService resourceUpdateService;

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
        return userUpdateService.userUpdate(userUpdateDto);
    }

    @GetMapping("/role/list")
    public ResponseEntity<ResponseDto<?>> roleGet(){
        return roleGetService.roleGet();
    }

    @GetMapping("/resource/list")
    public ResponseEntity<ResponseDto<?>> resourceGet(){
        return resourceGetService.resourceGet();
    }

    //ResourceRole-Mapping
    @GetMapping("/mapping/list")
    public ResponseEntity<ResponseDto<?>> mappingGet(){
        return mappingGetService.mappingGet();
    }

    @PostMapping("/resource/create")
    public ResponseEntity<ResponseDto<?>> resourceCreate(@RequestBody ResourceCreateDto resourceCreateDto){
        return resourceCreateService.resourceCreate(resourceCreateDto);
    }

    @PostMapping("/role/create")
    public ResponseEntity<ResponseDto<?>> roleCreate(@RequestBody RoleCreateDto roleCreateDto){
        return roleCreateService.roleCreate(roleCreateDto);
    }

    @PutMapping("/mapping/update")
    public ResponseEntity<ResponseDto<?>> mappingUpdate(@RequestBody MappingUpdateDto mappingUpdateDto){
        return mappingUpdateService.mappingUpdate(mappingUpdateDto);
    }


    @PatchMapping("/role/update")
    public ResponseEntity<ResponseDto<?>> roleUpdate(@RequestBody RoleUpdateDto roleUpdateDto){
        return roleUpdateService.roleUpdate(roleUpdateDto);
    }

    @DeleteMapping("/role/delete/{roleId}")
    public ResponseEntity<ResponseDto<?>> roleDelete(@PathVariable Long roleId){
        return roleDeleteService.roleDelete(roleId);
    }

    @DeleteMapping("/resource/delete/{resourceId}")
    public ResponseEntity<ResponseDto<?>> resourceDelete(@PathVariable Long resourceId){
        return resourceDeleteService.resourceDelete(resourceId);
    }

    @PatchMapping("/resource/update")
    public ResponseEntity<ResponseDto<?>> resourceUpdate(@RequestBody ResourceUpdateDto resourceUpdateDto){
        return resourceUpdateService.resourceUpdate(resourceUpdateDto);
    }
}
