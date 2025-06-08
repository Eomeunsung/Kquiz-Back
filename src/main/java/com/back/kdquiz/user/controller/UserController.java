package com.back.kdquiz.user.controller;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.SignInDto;
import com.back.kdquiz.user.dto.SignUpDto;
import com.back.kdquiz.user.service.MyProfileService;
import com.back.kdquiz.user.service.SignInService;
import com.back.kdquiz.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final MyProfileService myProfileService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto<?>> signUp(@RequestBody SignUpDto signUpDto){
        return signUpService.SignUp(signUpDto);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto<?>> signIn(@RequestBody SignInDto signInDto){
        return signInService.signIn(signInDto);
    }

    @GetMapping("/myprofile")
    public ResponseEntity<ResponseDto<?>> myProfile(@AuthenticationPrincipal CustomUserDetails userDetails){
        return  myProfileService.myProfile(userDetails);
    }

}
