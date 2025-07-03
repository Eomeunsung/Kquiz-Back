package com.back.kdquiz.exception.global;

import com.back.kdquiz.exception.questionException.QuestionNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuestionGlobalExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleQuestionNotFoundException(QuestionNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
