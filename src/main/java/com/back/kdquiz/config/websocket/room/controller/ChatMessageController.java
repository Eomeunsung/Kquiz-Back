package com.back.kdquiz.config.websocket.room.controller;

import com.back.kdquiz.config.websocket.room.dto.*;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.config.websocket.room.service.GamePlayService;
import com.back.kdquiz.config.websocket.room.service.KickService;
import com.back.kdquiz.config.websocket.room.service.LobbyService;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final KickService kickService;
    private final LobbyService lobbyService;
    private final GamePlayService gamePlayService;

    //전체
    @MessageMapping("/chat/{roomId}")
    public void lobbyMessage(ChatMessageDto chatMessageDto, @DestinationVariable String roomId){
        lobbyService.lobby(chatMessageDto, roomId);
    }

    //강퇴 개별
    @MessageMapping("/kick/{roomId}")
    public void kickUser(@Payload KickRequestDto kickRequestDto, @DestinationVariable String roomId){
        kickService.kick(kickRequestDto, roomId);
    }

    //게임 시작
    @MessageMapping("/game/{roomId}")
    public void gamePlay(@DestinationVariable String roomId, @Payload ScoreDto scoreDto){
        if(scoreDto.getType().equals("SCORE")){
            gamePlayService.gamePlayScore(roomId, scoreDto);
        }else if(scoreDto.getType().equals("END")){
            gamePlayService.gamePlayEnd(roomId);
        }
    }

    //question 가져오기
    @MessageMapping("/quiz/{roomId}")
    public void questionGet(@DestinationVariable String roomId, @Payload QuestionGetDto questionGetDto){
        log.info("퀘스천 question 아이디 "+questionGetDto.getId()+" "+questionGetDto.getContent());
        QuestionTypeDto questionTypeDto = new QuestionTypeDto();
        questionTypeDto.setQuestion(questionGetDto);
        questionTypeDto.setType(TypeEnum.QUESTION);
        log.info("게임 주소 "+roomId);
        messagingTemplate.convertAndSend("/topic/quiz/"+roomId, questionTypeDto);
    }

    //timer
    @MessageMapping("/timer/{roomId}")
    public void timer(@DestinationVariable String roomId, @Payload TimerDto timerDto){
        TimerDto response = new TimerDto();
        if(timerDto.getType().equals("READY")){     //게임 시작전 카운터
            response.setFlag(true);
            response.setType("READY");
            response.setTime(timerDto.getTime());
        }else if(timerDto.getType().equals("START")){ //카운터 끝난 후 게임 시작
            response.setFlag(false);
            response.setType("START");
            response.setTime(timerDto.getTime());
        }else if(timerDto.getType().equals("TIMER")){ //question 타이머
            response.setTime(timerDto.getTime());
            response.setType("TIMER");
        }

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, response);

    }


}
