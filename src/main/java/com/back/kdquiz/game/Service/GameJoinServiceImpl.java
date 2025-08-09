package com.back.kdquiz.game.Service;

import com.back.kdquiz.exception.gameException.GameNotFoundException;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.game.dto.GameJoinDto;
import com.back.kdquiz.game.dto.GameJoinResDto;
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
public class GameJoinServiceImpl implements GameJoinService {

    private final GameLobbyRedis gameLobbyRedis;

    @Transactional
    @Override
    public ResponseEntity gameJoinResponse(GameJoinDto gameJoinDto) {

        String gameId = gameLobbyRedis.getQuiz(gameJoinDto.getGameId());
//            Long id = Long.parseLong(gameId);
        log.info("서비스에서 게임 아이디 조회: "+gameId+" / "+gameJoinDto.getGameId());
        if(gameId==null || gameId.equals("")){
            throw new GameNotFoundException();
        }

        Long newId = gameLobbyRedis.addUser(gameJoinDto.getGameId(), gameJoinDto.getName());
        String quizTitle = gameLobbyRedis.quizTitleGet(gameJoinDto.getGameId());

        GameJoinResDto gameJoinResDto = GameJoinResDto
                .builder()
                .quizTitle(quizTitle)
                .userId(newId)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("P200", "게임 접속 성공", gameJoinResDto));

    }
}
