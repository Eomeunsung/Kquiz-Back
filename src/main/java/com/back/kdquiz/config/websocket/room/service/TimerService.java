package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.TimerResDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimerService {
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void readyCount(String roomId){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(3)
                .build();

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }

    @Transactional
    public void counter(String roomId, int time){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(time-1)
                .build();

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }

    @Transactional
    public void startQuiz(String roomId){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(0)
                .build();

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }

    @Transactional
    public void QuestionTimer(String roomId, int time){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.QUESTION_TIMER)
                .timer(time-1)
                .build();
        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }
}
