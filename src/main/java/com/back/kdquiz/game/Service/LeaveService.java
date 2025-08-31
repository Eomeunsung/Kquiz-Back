package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.Repository.GameRepositoryRedis;
import com.back.kdquiz.game.dto.room.ChatMessageDto;
import com.back.kdquiz.game.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveService {
    private final SimpMessagingTemplate messagingTemplate;

    private final GameRepositoryRedis gameRepositoryRedis;

    @Transactional
    public void leaveRoom(String roomId, String userId){
        String userName = gameRepositoryRedis.getUser(roomId, userId);

        gameRepositoryRedis.deleteUser(roomId, userId);

        Map<String, Object> users = gameRepositoryRedis. getAllUsers(roomId);

        ChatMessageDto chatMessageDto = ChatMessageDto
                .builder()
                .content(userName+" 님이 퇴장하셨습니다.")
                .type(TypeEnum.LEAVE)
                .userList(users)
                .build();

        messagingTemplate.convertAndSend(String.format("/topic/lobby/%s", roomId), chatMessageDto);
    }
}
