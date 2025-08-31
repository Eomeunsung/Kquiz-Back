package com.back.kdquiz.game.controller;

import com.back.kdquiz.game.Repository.GameRepositoryRedis;
import com.back.kdquiz.game.Service.*;
import com.back.kdquiz.game.dto.room.*;
import com.back.kdquiz.game.enums.TypeEnum;
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
public class RoomController {
    private final SimpMessagingTemplate messagingTemplate;
    private final KickService kickService;
    private final LobbyService lobbyService;
    private final GamePlayService gamePlayService;
    private final TimerService timerService;
    private final GameRepositoryRedis gameRepositoryRedis;
    private final QuestionGetService questionGetService;
    private final LeaveService leaveService;


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

    //방 나가기
    @MessageMapping("/leave/{roomId}/{userId}")
    public void leaveRoom(@DestinationVariable String roomId, @DestinationVariable String userId){
        leaveService.leaveRoom(roomId, userId);
    }

    //게임 시작
    @MessageMapping("/game/{roomId}")
    public void gamePlay(@DestinationVariable String roomId, @Payload ScoreDto scoreDto){
        if(scoreDto.getType().equals("SCORE")){
            gamePlayService.gamePlayScore(roomId, scoreDto);
        }else if(scoreDto.getType().equals("GAME_OVER")){
            gamePlayService.gameOver(roomId);
        }
    }

    //question 가져오기
    @MessageMapping("/quiz/{roomId}")
    public void questionGet(@DestinationVariable String roomId, @Payload QuestionKeyDto questionKeyDto){
        log.info("받아온 question_MAP 키값 "+questionKeyDto.getQuestionKey());
        String questionIndex = String.valueOf(questionKeyDto.getQuestionKey()+1);
        Long questionId = gameRepositoryRedis.findQuestionIndex(roomId, questionIndex);
        log.info("가져온 퀘스천 아이디 "+questionId);
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
            timerService.questionTimer(roomId, timerDto.getTime());
        }else if(timerDto.getType().equals("QUESTION_LAST_TIMER")){
            timerService.questionLastTimer(roomId, timerDto.getTime());
        }

    }
}
