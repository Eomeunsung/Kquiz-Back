package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.ChatMessageDto;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService{
    private final GameLobbyRedis gameLobbyRedis;

    @Transactional
    public void joinRoom(String roomId, String userId, String name){
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        if(userId==null || userId.equals("null")){
            Long newId =  gameLobbyRedis.addUser(roomId,name);
            chatMessageDto.setUserId(newId);
            log.info("[SessionConnected]: nickname = " + name);
            log.info("룸 아이디 "+roomId);
            Map<String, Object> users = gameLobbyRedis.getAllUsers(roomId);

            chatMessageDto.setName(name);
            chatMessageDto.setUserList(users);
            chatMessageDto.setContent(name+" 님이 참가하였습니다.");
            log.info("메시지 전송 {}",chatMessageDto.getUserId());
        }else {
            Long Id = Long.parseLong(userId);
            chatMessageDto.setUserId(Id);
        }

    }

}
