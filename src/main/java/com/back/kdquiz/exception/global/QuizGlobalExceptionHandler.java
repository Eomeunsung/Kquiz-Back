package com.back.kdquiz.exception.global;

import com.back.kdquiz.exception.quizException.QuizCreateFailedException;
import com.back.kdquiz.exception.quizException.QuizNotFoundException;
import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class QuizGlobalExceptionHandler {

    @ExceptionHandler(QuizCreateFailedException.class)
    public ResponseEntity<ResponseDto<?>> handleQuizCreateFailedException(QuizCreateFailedException ex){
        log.error("퀴즈 생성 실패: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleQuizNotFoundException(QuizNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
