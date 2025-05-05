package com.back.kdquiz.game.controller;

import com.back.kdquiz.game.Service.ParticipationService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
public class ParticipationController {
    private final ParticipationService participationService;

    @GetMapping("/participation/{roomId}")
    public ResponseEntity<ResponseDto<?>> participation(@PathVariable String roomId){
        ResponseDto responseDto = participationService.participation(roomId);
        if(responseDto.getCode().equals("P200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
