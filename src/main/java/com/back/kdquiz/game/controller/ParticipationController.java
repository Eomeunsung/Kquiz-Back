package com.back.kdquiz.game.controller;

import com.back.kdquiz.game.Service.ParticipationService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
@Slf4j
public class ParticipationController {
    private final ParticipationService participationService;

    @GetMapping("/participation/{roomId}")
    public ResponseEntity<ResponseDto<?>> participation(@PathVariable String roomId){
        log.info("게임 아이디 "+roomId);
        return participationService.participationResponse(roomId);
    }
}
