package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.dto.room.ChatMessageDto;
import com.back.kdquiz.game.dto.room.KickRequestDto;
import com.back.kdquiz.game.enums.TypeEnum;
import com.back.kdquiz.game.Repository.GameRepositoryRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KickService {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameRepositoryRedis gameRepositoryRedis;

    public void kick(KickRequestDto kickRequestDto, String roomId){
        log.info("강퇴 게임 아이디 "+roomId);
        String userId = kickRequestDto.getUserId();
        log.info("강퇴 유저 아이디 "+userId);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        String userName = gameRepositoryRedis.getUser(roomId, userId); //유저 이름 찾기
        gameRepositoryRedis.removeUser(roomId, userId); //유저 제거
        Map<String, Object> users = gameRepositoryRedis.getAllUsers(roomId);

        chatMessageDto.setUserList(users);
        chatMessageDto.setContent(userName+" 님이 강퇴 당했습니다.");
        chatMessageDto.setType(TypeEnum.KICK);

        messagingTemplate.convertAndSend("/topic/lobby/" + roomId, chatMessageDto);
        messagingTemplate.convertAndSend("/topic/kick/" + roomId+"/"+userId, "KICKED");
    }
}
