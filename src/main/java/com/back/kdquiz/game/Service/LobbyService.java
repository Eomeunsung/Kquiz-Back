package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.dto.room.LobbyReqDto;
import com.back.kdquiz.game.dto.room.LobbyResDto;
import com.back.kdquiz.game.enums.TypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LobbyService {

    private final SimpMessagingTemplate messagingTemplate;

    public void lobby(LobbyReqDto lobbyReqDto, String roomId){
        String destination = "/topic/lobby/"+roomId;
        log.info("타입 "+lobbyReqDto.getContent()+" roomId "+roomId);

        if(lobbyReqDto.getContent().equals("GAME")){
            LobbyResDto lobbyResDto = LobbyResDto
                    .builder()
                    .typeEnum(TypeEnum.GAME)
                    .build();
            messagingTemplate.convertAndSend(destination, lobbyResDto);
        }
//
//        if(chatMessageDto.getContent().equals(TypeEnum.GAME.name())){
//            GameRequestDto gameRequestDto = new GameRequestDto();
//            gameRequestDto.setType(TypeEnum.GAME);
//            gameRequestDto.setContent("게임 시작");
//            messagingTemplate.convertAndSend(destination, gameRequestDto);
//        }else{
//            log.info("들어온 DTO "+chatMessageDto.getContent()+" 아이디 "+roomId +" 이름 "+chatMessageDto.getName());
//            log.info("보낼 주소 "+destination);
//            messagingTemplate.convertAndSend(destination, chatMessageDto);
//        }
    }
}
