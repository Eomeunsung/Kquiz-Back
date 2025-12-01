package com.back.kdquiz.game.controller;

import com.back.kdquiz.game.Service.GameCreateService;
import com.back.kdquiz.game.Service.GameJoinService;
import com.back.kdquiz.game.dto.GameCreateDto;
import com.back.kdquiz.game.dto.GameJoinDto;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Log4j2
public class GameController {

    private final GameCreateService gameCreateService;
    private final GameJoinService gameJoinService;


    @GetMapping("/create/{quizId}")
    public ResponseEntity<ResponseDto<GameCreateDto>> gameCreate(@PathVariable Long quizId){
        log.info("퀴즈 아이디 "+quizId);
        return gameCreateService.gameCreateResponse(quizId);
    }


    @PostMapping("/join")
    public ResponseEntity<ResponseDto<?>> gameJoin(@RequestBody GameJoinDto gameJoinDto){
        log.info("게임 아이디 "+gameJoinDto.getGameId());
        return gameJoinService.gameJoinResponse(gameJoinDto);
    }
}
