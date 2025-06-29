package com.back.kdquiz.user.service;

import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.config.security.jwt.JwtUtil;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseEntity<ResponseDto<?>> refreshToken(String token, CustomUserDetails customUserDetails){
        ResponseDto responseDto;
        boolean tokenFlag = tokenExpired10Minutes(token);

        if(!tokenFlag){
            responseDto = ResponseDto.setSuccess("T201", "토큰 아직 유효");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            String refreshToken = refreshTokenRepository.get(customUserDetails.getUsername());
            String newRefreshToken = jwtUtil.refreshCreateToken(customUserDetails);

            refreshTokenRepository.save(customUserDetails.getUsername(), newRefreshToken);

            responseDto = ResponseDto.setSuccess("T200", "토큰 재발급", refreshToken);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }


    private Boolean tokenExpired10Minutes(String token) {
        // 현재 시간에서 10분 전의 시간 생성
        Date tenMinutesLater = new Date(System.currentTimeMillis() + (1000 * 60 * 10));
        // 만료 시간이 10분 전보다 이전인지 확인
        return jwtUtil.extractExpired(token).before(tenMinutesLater);
    }
}
