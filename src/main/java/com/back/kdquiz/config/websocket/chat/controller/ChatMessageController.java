package com.back.kdquiz.config.websocket.chat.controller;

import com.back.kdquiz.config.websocket.chat.dto.ChatMessageDto;
import com.back.kdquiz.config.websocket.chat.dto.KickRequestDto;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;
    public ChatMessageController(SimpMessagingTemplate messagingTemplate, GameLobbyRedis gameLobbyRedis) {
        this.messagingTemplate = messagingTemplate;
        this.gameLobbyRedis = gameLobbyRedis;
    }

//    @MessageMapping("/chat")
//    @SendTo("/topic")
//    public ChatMessageDto sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
//        ChatMessageDto chatResponse =  new ChatMessageDto();
//        chatResponse.setContent(chatMessageDto.getContent());
//        chatResponse.setName(chatMessageDto.getName());
//        return chatResponse;
//    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
        log.info("들어온 DTO "+chatMessageDto.getContent()+" 아이디 "+roomId +" 이름 "+chatMessageDto.getName());
        String destination = "/topic/chat/"+roomId;
        log.info("보낼 주소 "+destination);
        messagingTemplate.convertAndSend(destination, chatMessageDto);
    }

    //강퇴
    @MessageMapping("/kick")
    public void kickUser(@Payload KickRequestDto request){
        String gameId = request.getGameId();
        log.info("게임 아이디 "+gameId);
        String userId = request.getUserId();
        log.info("유저 아이디 "+userId);
//        gameLobbyRedis.removeUser(gameId, userId);
        messagingTemplate.convertAndSendToUser(userId,"/queue/kick", "KICKED");
    }
}
