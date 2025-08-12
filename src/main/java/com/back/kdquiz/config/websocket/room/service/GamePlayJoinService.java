package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.TimerResDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GamePlayJoinService {

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void gameJoinService(String roomId){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(5)
                .build();

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }
}
