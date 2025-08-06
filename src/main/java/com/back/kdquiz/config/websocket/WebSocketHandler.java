package com.back.kdquiz.config.websocket;

import com.back.kdquiz.config.websocket.room.dto.ChatMessageDto;
import com.back.kdquiz.config.websocket.room.dto.GameRequestDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.config.websocket.room.service.JoinService;
import com.back.kdquiz.config.websocket.room.service.LobbyService;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.quiz.dto.get.QuestionGetIdDto;
import com.back.kdquiz.quiz.service.questionService.get.QuestionGetIdService;
import com.back.kdquiz.quiz.service.quizService.get.QuizGetServiceImpl;
import com.back.kdquiz.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;
    private final QuestionGetIdService questionGetIdService;
    private final JoinService joinService;

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
            joinService.joinRoom(roomId, userId, name);
        }

        //GAME 으로 접속 했을 경우
        else if(TypeEnum.valueOf(type)==TypeEnum.GAME){
            GameRequestDto gameRequestDto = new GameRequestDto();
            gameRequestDto.setContent("곧 있으면 게임이 시작 됩니다.");
            gameRequestDto.setType(TypeEnum.GAME);
            String strId = gameLobbyRedis.getQuiz(roomId);
            Long quizId = Long.parseLong(strId);
            QuestionGetIdDto questionGetIdDto = new QuestionGetIdDto();
            ResponseDto responseDto = questionGetIdService.questionGetId(quizId);
            if(responseDto.getCode().equals("Q200")){
                questionGetIdDto = (QuestionGetIdDto) responseDto.getData();
            }
            //바로 클라이언트로 보내면 받지 못할 수도 있음으로 딜레이 주면서 보내기
            QuestionGetIdDto finalQuestionGetIdDto = questionGetIdDto;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    messagingTemplate.convertAndSend(String.format("/topic/game/%s", roomId), gameRequestDto);
                    messagingTemplate.convertAndSend(String.format("/topic/quiz/%s",roomId), finalQuestionGetIdDto);
                }
            }, 200); // 200ms 딜레이
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
