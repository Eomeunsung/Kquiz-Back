package com.back.kdquiz.game.controller;

import com.back.kdquiz.game.Service.GameCreateService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {

    private final GameCreateService gameCreateService;

    @GetMapping("/create/{quizId}")
    public ResponseEntity<ResponseDto<String>> gameCreate(@PathVariable Long quizId){
        ResponseDto<String> responseDto = gameCreateService.gameCreate();

        if(responseDto.getCode().equals("G200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
