package com.back.kdquiz.config.websocket.chat.controller;

import com.back.kdquiz.config.websocket.chat.dto.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
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
}
