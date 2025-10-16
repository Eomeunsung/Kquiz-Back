package com.back.kdquiz.exception.global;

import com.back.kdquiz.exception.resourceException.ResourceDuplicationException;
import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ResourceGlobalException {
    @ExceptionHandler(ResourceDuplicationException.class)
    public ResponseEntity<ResponseDto<?>> handlerRoleNotFoundException(ResourceDuplicationException ex){
        log.info("에러 "+ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
