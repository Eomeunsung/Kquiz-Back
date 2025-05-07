package com.back.kdquiz.config.websocket;

import com.back.kdquiz.config.websocket.chat.dto.ChatMessageDto;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        log.info("들어온 메시지 "+event);
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        String roomId = accessor.getFirstNativeHeader("roomId");
        String name = accessor.getFirstNativeHeader("name");

       Long userId =  gameLobbyRedis.addUser(roomId,name);

        log.info("[SessionConnected]: nickname = " + name);
        log.info("룸 아이디 "+roomId);
        Map<Object, Object> users = gameLobbyRedis.getAllUsers(roomId);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setUserId(userId);
        chatMessageDto.setName(name);
        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(name+" 님이 참가하였습니다.");
        log.info("메시지 전송 {}",chatMessageDto);
        messagingTemplate.convertAndSend(String.format("/topic/chat/%s", roomId), chatMessageDto);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        String userId = accessor.getFirstNativeHeader("userId");
        String roomId = accessor.getFirstNativeHeader("roomId");
        String name = accessor.getFirstNativeHeader("name");

        log.info("[SessionDisconnected]: "+name);

        // 시스템 메시지를 생성하고 발행합니다.
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setName(name);
        chatMessageDto.setContent(name+" 님이 나갔습니다.");

        messagingTemplate.convertAndSend(String.format("/topic/chat/%s", roomId), chatMessageDto);
    }
}
