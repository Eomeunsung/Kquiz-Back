package com.back.kdquiz.user.service;

import com.back.kdquiz.config.custom.CustomUserDetailService;
import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.config.security.jwt.JwtUtil;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.SignInDto;
import com.back.kdquiz.user.dto.TokenDto;
import com.back.kdquiz.user.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final UsersRepository usersRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final CustomUserDetailService customUserDetailService;

    @Transactional
    public ResponseEntity<ResponseDto<?>> signIn(SignInDto signInDto){
        ResponseDto responseDto;
        try{
            Users users = usersRepository.findByEmail(signInDto.getEmail());
            if(users==null){
                responseDto = ResponseDto.setFailed("U000", "이메일이나 비밀번호를 다시 입력해 주세요.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            if(!passwordEncoder.matches(signInDto.getPassword(), users.getPassword())){
                responseDto = ResponseDto.setFailed("U001", "이메일이나 비밀번호를 다시 입력해 주세요.");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            final CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(users.getEmail());
            final String accessToken = jwtUtil.createToken(customUserDetails);
            final String refreshToken = jwtUtil.refreshCreateToken(customUserDetails);

            refreshTokenRepository.save(users.getEmail(), refreshToken);

            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(accessToken);
            tokenDto.setNickName(users.getNickName());

            responseDto = ResponseDto.setSuccess("U200", "로그인 성공", tokenDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            responseDto = ResponseDto.setFailed("U002", "알 수 없는 오류 발생 "+e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
