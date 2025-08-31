package com.back.kdquiz.config.websocket;

import com.back.kdquiz.game.dto.room.ChatMessageDto;
import com.back.kdquiz.game.enums.TypeEnum;
import com.back.kdquiz.game.Service.GamePlayJoinService;
import com.back.kdquiz.game.Service.LobbyJoinService;
import com.back.kdquiz.game.Service.TimerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private final LobbyJoinService lobbyJoinService;
    private final GamePlayJoinService gamePlayJoinService;
    private final TimerService timerService;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        log.info("들어온 메시지 "+event);
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        String roomId = accessor.getFirstNativeHeader("roomId");
        String name = accessor.getFirstNativeHeader("name");
        String userId = accessor.getFirstNativeHeader("userId");
        String type = accessor.getFirstNativeHeader("type");

        log.info("타입 "+type+" "+type.equals(TypeEnum.LOBBY));

        log.info("웹 소켓 연결된 룸 아이디 "+roomId);
        //LOBBY 방 참여
        if(TypeEnum.valueOf(type) == TypeEnum.LOBBY){
            lobbyJoinService.joinRoom(roomId, userId, name);
        }
        else if(TypeEnum.valueOf(type)==TypeEnum.GAME){
            gamePlayJoinService.gameJoinService(roomId);
        }
        else if(TypeEnum.valueOf(type) == TypeEnum.GAME_START){
            timerService.readyCount(roomId);

        }

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
