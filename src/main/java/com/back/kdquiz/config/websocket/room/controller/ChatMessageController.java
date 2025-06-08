package com.back.kdquiz.config.websocket.room.controller;

import com.back.kdquiz.config.websocket.room.dto.*;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.questionService.QuestionGetService;
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
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;

    private final QuestionGetService questionGetService;
    public ChatMessageController(SimpMessagingTemplate messagingTemplate, GameLobbyRedis gameLobbyRedis, QuestionGetService questionGetService) {
        this.messagingTemplate = messagingTemplate;
        this.gameLobbyRedis = gameLobbyRedis;
        this.questionGetService = questionGetService;
    }

//    @MessageMapping("/chat")
//    @SendTo("/topic")
//    public ChatMessageDto sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
//        ChatMessageDto chatResponse =  new ChatMessageDto();
//        chatResponse.setContent(chatMessageDto.getContent());
//        chatResponse.setName(chatMessageDto.getName());
//        return chatResponse;
//    }

    //전체
    @MessageMapping("/chat/{roomId}")
    public void sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable String roomId){
        String destination = "/topic/chat/"+roomId;
        if(chatMessageDto.getContent().equals("GAME")){
            GameRequestDto gameRequestDto = new GameRequestDto();
            gameRequestDto.setType(TypeEnum.GAME);
            gameRequestDto.setContent("게임 시작");

            messagingTemplate.convertAndSend(destination, gameRequestDto);
        }else{
            log.info("들어온 DTO "+chatMessageDto.getContent()+" 아이디 "+roomId +" 이름 "+chatMessageDto.getName());

            log.info("보낼 주소 "+destination);

            messagingTemplate.convertAndSend(destination, chatMessageDto);
        }

    }

    //강퇴 개별
    @MessageMapping("/kick")
    public void kickUser(@Payload KickRequestDto request){
        String gameId = request.getGameId();
        log.info("강퇴 게임 아이디 "+gameId);
        String userId = request.getUserId();
        log.info("강퇴 유저 아이디 "+userId);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        String userName = gameLobbyRedis.getUser(gameId, userId); //유저 이름 찾기
        gameLobbyRedis.removeUser(gameId, userId); //유저 제거
        Map<String, Object> users = gameLobbyRedis.getAllUsers(gameId);

        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(userName+" 님이 강퇴 당했습니다.");
        chatMessageDto.setType(TypeEnum.KICK);

        messagingTemplate.convertAndSend("/topic/chat/" + gameId, chatMessageDto);
        messagingTemplate.convertAndSend("/topic/kick/" + userId, "KICKED");
    }

    //게임 시작
    @MessageMapping("/game/{roomId}")
    public void startGame(@DestinationVariable String roomId, @Payload ScoreDto scoreDto){
        if(scoreDto.getType().equals("SCORE")){
            gameLobbyRedis.addScore(roomId, scoreDto.getUserId(), scoreDto.getScore());
            log.info("유저 점수 "+gameLobbyRedis.getScore(roomId, scoreDto.getUserId())+"유저 아이디 "+scoreDto.getUserId());
            messagingTemplate.convertAndSend("/topic/game/"+roomId, "채점 완료");
        }else if(scoreDto.getType().equals("END")){
            Map<String, Object> scoreMap = gameLobbyRedis.getAllScores(roomId);
            Map<String, Object> userMap = gameLobbyRedis.getAllUsers(roomId);
            UserScoreDto result = new UserScoreDto();
            List<EndScoreDto> endScoreDtos = new ArrayList<>();
             for(Map.Entry<String, Object> entry : userMap.entrySet()){
                String userIndex = entry.getKey().toString();
                String username = entry.getValue().toString();
                if(username.equals("HOST")){
                    continue;
                }
                Object scoreObj = scoreMap.get(userIndex);
                int score = scoreObj != null ? Integer.parseInt(scoreObj.toString()) : 0;
                endScoreDtos.add(new EndScoreDto(username, score));
            }

            // 점수 기준 내림차순 정렬
            endScoreDtos.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

             result.setScores(endScoreDtos);
             result.setType(TypeEnum.SCORE);
             gameLobbyRedis.gameDelete(roomId);
            messagingTemplate.convertAndSend("/topic/game/"+roomId, result);
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
