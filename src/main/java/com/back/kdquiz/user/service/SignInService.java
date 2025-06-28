package com.back.kdquiz.user.service;

import com.back.kdquiz.config.custom.CustomUserDetailService;
import com.back.kdquiz.config.custom.CustomUserDetails;
import com.back.kdquiz.config.security.jwt.JwtUtil;
import com.back.kdquiz.domain.entity.Users;
import com.back.kdquiz.domain.repository.UsersRepository;
import com.back.kdquiz.response.ResponseDto;
import com.back.kdquiz.user.dto.SignInDto;
import com.back.kdquiz.user.dto.TokenDto;
import com.back.kdquiz.exception.userException.UserDisabledException;
import com.back.kdquiz.exception.userException.UserNotFoundException;
import com.back.kdquiz.exception.userException.WrongPasswordException;
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

        Users users = usersRepository.findByEmail(signInDto.getEmail());
        if(users==null){
            throw new UserNotFoundException();
        }

        if(!passwordEncoder.matches(signInDto.getPassword(), users.getPassword())){
            throw new WrongPasswordException();
        }

        final CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(users.getEmail());
        if(!customUserDetails.isEnabled()){
            throw new UserDisabledException();
        }
        final String accessToken = jwtUtil.createToken(customUserDetails);
        final String refreshToken = jwtUtil.refreshCreateToken(customUserDetails);

        refreshTokenRepository.save(users.getEmail(), refreshToken);

        TokenDto tokenDto = new TokenDto(accessToken, users.getNickName());

        return new ResponseEntity<>( ResponseDto.setSuccess("U200", "로그인 성공", tokenDto)
                , HttpStatus.OK);
    }
}
