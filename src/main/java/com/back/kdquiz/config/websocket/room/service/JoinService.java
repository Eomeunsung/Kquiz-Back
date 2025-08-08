package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.ChatMessageDto;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService{
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;

    @Transactional
    public void joinRoom(String roomId, String userId, String name){
        log.info("들어온 값 "+roomId+" "+userId+" "+name);
        ChatMessageDto chatMessageDto = new ChatMessageDto();

        Map<String, Object> users = gameLobbyRedis. getAllUsers(roomId);

        if(users.isEmpty()){
            log.info("유저가 비어 있는 오류 발생");
        }
        for (Map.Entry<String, Object> entry : users.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }


        chatMessageDto.setName(name);
        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(name+" 님이 참가하였습니다.");
        log.info("메시지 전송 {}",chatMessageDto.getUserId());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messagingTemplate.convertAndSend(String.format("/topic/lobby/%s", roomId), chatMessageDto);
            }
        }, 200); // 200ms 딜레이

    }

}
