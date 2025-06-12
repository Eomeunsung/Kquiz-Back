package com.back.kdquiz.user.controller;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.RefreshTokenDto;
import com.back.kdquiz.user.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto<?>> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("리프레쉬 토큰 "+refreshTokenDto.getToken());
        return refreshTokenService.refreshToken(refreshTokenDto.getToken(), customUserDetails);
    }
}
