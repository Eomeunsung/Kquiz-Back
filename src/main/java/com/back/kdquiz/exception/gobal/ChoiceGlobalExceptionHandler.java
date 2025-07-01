package com.back.kdquiz.exception.gobal;

import com.back.kdquiz.exception.choiceException.ChoiceCreateFailedException;
import com.back.kdquiz.exception.choiceException.ChoiceNotFoundException;
import com.back.kdquiz.exception.choiceException.ChoiceSaveFailedException;
import com.back.kdquiz.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ChoiceGlobalExceptionHandler {
    @ExceptionHandler(ChoiceCreateFailedException.class)
    public ResponseEntity<ResponseDto<?>> handleChoiceCreateFailedException(ChoiceCreateFailedException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(ChoiceNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleChoiceNotFoundException(ChoiceNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(ChoiceSaveFailedException.class)
    public ResponseEntity<ResponseDto<?>> handleChoiceSaveFailedException(ChoiceSaveFailedException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
