package com.back.kdquiz.config.websocket.chat.controller;

import com.back.kdquiz.config.websocket.chat.dto.*;
import com.back.kdquiz.config.websocket.chat.enums.TypeEnum;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import com.back.kdquiz.quiz.service.questionService.QuestionGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
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
        Map<Object, Object> users = gameLobbyRedis.getAllUsers(gameId);

        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(userName+" 님이 강퇴 당했습니다.");
        chatMessageDto.setType(TypeEnum.KICK);

        messagingTemplate.convertAndSend("/topic/chat/" + gameId, chatMessageDto);
        messagingTemplate.convertAndSend("/topic/kick/" + userId, "KICKED");
    }

    //게임 시작
    @MessageMapping("/game/{roomId}")
    public void startGame(@DestinationVariable String roomId){
        GameRequestDto gameRequestDto = new GameRequestDto();
        gameRequestDto.setContent("게임이 시작됩니다.");
        gameRequestDto.setType(TypeEnum.GAME);
        log.info("게임 주소 "+roomId);
        messagingTemplate.convertAndSend("/topic/game/"+roomId,gameRequestDto);
    }

    //question가져오기
    @MessageMapping("/quiz/{roomId}")
    public void questionGet(@DestinationVariable String roomId, @Payload QuestionIdDto questionId){
        log.info("퀘스천 question 아이디 "+questionId.getQuestionId());
        ResponseDto responseDto = questionGetService.questionGet(questionId.getQuestionId());
        QuestionTypeDto questionTypeDto = new QuestionTypeDto();
        questionTypeDto.setType(TypeEnum.QUESTION);
        questionTypeDto.setQuestion((QuestionGetDto) responseDto.getData());
        log.info("게임 주소 "+roomId);
        messagingTemplate.convertAndSend("/topic/quiz/"+roomId, questionTypeDto);
    }


}
