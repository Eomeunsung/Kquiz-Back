package com.back.kdquiz.exception;

import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DataExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto<?>> dataBaseException(DataAccessException ex){
        log.error("데이터 베이스 오류 발생 "+ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.setFailed("DB000", "서버에서 오류가 발생했습니다."));

    }

}
