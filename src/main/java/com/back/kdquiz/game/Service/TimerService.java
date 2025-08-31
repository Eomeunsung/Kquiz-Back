package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.dto.room.TimerResDto;
import com.back.kdquiz.game.enums.TypeEnum;
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
    private final GamePlayService gamePlayService;
    
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
        }, 1000); // 1초 딜레이
    }

    @Transactional
    public void counter(String roomId, int time){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.READY_COUNT)
                .timer(time-1)
                .build();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
            }
        }, 1000); // 1초 딜레이
    }

    @Transactional
    public void startQuiz(String roomId){
        TimerResDto timerResDto = TimerResDto
                .builder()
                .type(TypeEnum.START)
                .timer(0)
                .build();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
            }
        }, 1000); // 1초 딜레이
    }

    @Transactional
    public void questionTimer(String roomId, int time){
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

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
            }
        }, 1000); // 1초 딜레이
    }

    @Transactional
    public void questionLastTimer(String roomId, int time){
        TimerResDto timerResDto;
        if(time > 0){
            timerResDto = TimerResDto
                    .builder()
                    .type(TypeEnum.QUESTION_TIMER)
                    .timer(time-1)
                    .build();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    messagingTemplate.convertAndSend("/topic/timer/"+roomId, timerResDto);
                }
            }, 1000); // 1초 딜레이
        }else {
            gamePlayService.gameOver(roomId);
        }


    }
}
