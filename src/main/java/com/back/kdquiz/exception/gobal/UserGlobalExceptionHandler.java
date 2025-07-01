package com.back.kdquiz.exception.gobal;

import com.back.kdquiz.exception.userException.*;
import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserGlobalExceptionHandler {

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ResponseDto<?>> handleUserDisabledException(UserDisabledException ex){
        log.error("계정 정지: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleUserNotFoundException(UserNotFoundException ex){
        log.error("이메일 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ResponseDto<?>> handleWrongPasswordException(WrongPasswordException ex){
        log.error("비밀번호 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<ResponseDto<?>> handlerUserProfileException(UserProfileException ex){
        log.error("비밀번호 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDto<?>> handlerEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

}
