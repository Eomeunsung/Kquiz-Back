package com.back.kdquiz.user.controller;

import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.SignUpDto;
import com.back.kdquiz.user.service.SignUpService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto<?>> signUp(@RequestBody SignUpDto signUpDto){
        return signUpService.SignUp(signUpDto);
    }


}
