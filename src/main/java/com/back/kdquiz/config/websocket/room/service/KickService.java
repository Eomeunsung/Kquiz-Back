package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.ChatMessageDto;
import com.back.kdquiz.config.websocket.room.dto.KickRequestDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KickService {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;

    public void kick(KickRequestDto kickRequestDto, String roomId){
        log.info("강퇴 게임 아이디 "+roomId);
        String userId = kickRequestDto.getUserId();
        log.info("강퇴 유저 아이디 "+userId);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        String userName = gameLobbyRedis.getUser(roomId, userId); //유저 이름 찾기
        gameLobbyRedis.removeUser(roomId, userId); //유저 제거
        Map<String, Object> users = gameLobbyRedis.getAllUsers(roomId);

        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(userName+" 님이 강퇴 당했습니다.");
        chatMessageDto.setType(TypeEnum.KICK);

        messagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessageDto);
        messagingTemplate.convertAndSend("/topic/kick/" + roomId+"/"+userId, "KICKED");
    }
}
