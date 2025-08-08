package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.ChatMessageDto;
import com.back.kdquiz.config.websocket.room.dto.GameRequestDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LobbyService {

    private final SimpMessagingTemplate messagingTemplate;

    public void lobby(ChatMessageDto chatMessageDto, String roomId){
        String destination = "/topic/lobby/"+roomId;

        if(TypeEnum.valueOf(chatMessageDto.getType())==TypeEnum.GAME)
        if(chatMessageDto.getContent().equals(TypeEnum.GAME.name())){
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
}
