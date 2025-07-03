package com.back.kdquiz.exception.global;

import com.back.kdquiz.exception.roleException.RoleNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RoleGlobalExceptionHandler {
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handlerRoleNotFoundException(RoleNotFoundException ex){
        log.error("권한 에러 / 권한이 DB에 누락: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
