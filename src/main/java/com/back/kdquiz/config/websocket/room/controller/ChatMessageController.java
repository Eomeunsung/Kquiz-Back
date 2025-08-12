package com.back.kdquiz.config.websocket.room.controller;

import com.back.kdquiz.config.websocket.room.dto.*;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.config.websocket.room.service.GamePlayService;
import com.back.kdquiz.config.websocket.room.service.KickService;
import com.back.kdquiz.config.websocket.room.service.LobbyService;
import com.back.kdquiz.config.websocket.room.service.TimerService;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final KickService kickService;
    private final LobbyService lobbyService;
    private final GamePlayService gamePlayService;
    private final TimerService timerService;
    private final GameLobbyRedis gameLobbyRedis;
    private final QuestionGetService questionGetService;


    //전체
    @MessageMapping("/lobby/{roomId}")
    public void lobbyMessage(@Payload LobbyReqDto lobbyReqDto, @DestinationVariable String roomId){
        lobbyService.lobby(lobbyReqDto, roomId);
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
    public void questionGet(@DestinationVariable String roomId, @Payload Long questionKey){
        String questionIndex = String.valueOf(questionKey);
        Long questionId = gameLobbyRedis.findQuestionIndex(roomId, questionIndex);
        QuestionGetDto questionGetDto = questionGetService.questionGetDto(questionId);
        QuestionTypeDto questionTypeDto = QuestionTypeDto
                .builder()
                .question(questionGetDto)
                .type(TypeEnum.QUESTION)
                .build();
        log.info("게임 주소 "+roomId);
        messagingTemplate.convertAndSend("/topic/quiz/"+roomId, questionTypeDto);
    }

    //timer
    @MessageMapping("/timer/{roomId}")
    public void timer(@DestinationVariable String roomId, @Payload TimerDto timerDto){
        log.info("받은 방 번호 "+roomId+" timer "+timerDto.getTime());
//        TimerDto response = new TimerDto();
        if(timerDto.getType().equals("READY")){     //게임 시작전 카운터
            timerService.readyCount(roomId);
        }else if(timerDto.getType().equals("READY_COUNT") && timerDto.getTime()>0){
            timerService.counter(roomId, timerDto.getTime());
        } else if(timerDto.getType().equals("READY_COUNT") && timerDto.getTime() == 0){ //카운터 끝난 후 게임 시작
            timerService.startQuiz(roomId);
        }else if(timerDto.getType().equals("QUESTION_TIMER")){ //question 타이머
            timerService.QuestionTimer(roomId, timerDto.getTime());
        }

//        messagingTemplate.convertAndSend("/topic/timer/"+roomId, response);

    }
    //

}
