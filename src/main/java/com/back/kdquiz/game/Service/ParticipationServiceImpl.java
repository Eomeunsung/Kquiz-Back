package com.back.kdquiz.game.Service;

import com.back.kdquiz.exception.gameException.GameNotFoundException;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ParticipationServiceImpl implements ParticipationService{

    private final GameLobbyRedis gameLobbyRedis;

    @Transactional
    @Override
    public ResponseEntity participationResponse(String roomId) {

        String gameId = gameLobbyRedis.getQuiz(roomId);
//            Long id = Long.parseLong(gameId);
        if(gameId==null || gameId.equals("")){
            throw new GameNotFoundException();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("P200", "게임 접속 성공"));

    }
}
