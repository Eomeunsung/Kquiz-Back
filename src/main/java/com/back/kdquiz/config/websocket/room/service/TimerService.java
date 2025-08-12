package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.TimerResDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimerService {
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void readyCount(String roomId){
        log.info(" Ready Counter 방 번호 "+roomId);
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(5)
                .build();

//        messagingTemplate.convertAndSend("/timer/"+roomId, timerResDto);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
            }
        }, 200); // 200ms 딜레이
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
                .type(TypeEnum.START)
                .timer(0)
                .build();

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }

    @Transactional
    public void QuestionTimer(String roomId, int time){
        TimerResDto timerResDto;
        if(time > 0){
            timerResDto = TimerResDto
                    .builder()
                    .type(TypeEnum.QUESTION_TIMER)
                    .timer(time-1)
                    .build();
        }else {
            timerResDto = TimerResDto
                    .builder()
                    .type(TypeEnum.QUESTION)
                    .timer(0)
                    .build();
        }

        messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
    }
}
