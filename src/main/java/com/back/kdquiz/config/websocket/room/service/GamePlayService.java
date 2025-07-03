package com.back.kdquiz.config.websocket.room.service;

import com.back.kdquiz.config.websocket.room.dto.EndScoreDto;
import com.back.kdquiz.config.websocket.room.dto.ScoreDto;
import com.back.kdquiz.config.websocket.room.dto.UserScoreDto;
import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.game.Repository.GameLobbyRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GamePlayService {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameLobbyRedis gameLobbyRedis;

    public void gamePlayScore(String roomId, ScoreDto scoreDto) {
        gameLobbyRedis.addScore(roomId, scoreDto.getUserId(), scoreDto.getScore());
        int score = gameLobbyRedis.getScore(roomId,scoreDto.getUserId());
        log.info("유저 점수 " + gameLobbyRedis.getScore(roomId, scoreDto.getUserId()) + "유저 아이디 " + scoreDto.getUserId());
        messagingTemplate.convertAndSend("/topic/game/" +scoreDto.getUserId(), score);
    }

    public void gamePlayEnd(String roomId){
        Map<String, Object> scoreMap = gameLobbyRedis.getAllScores(roomId);
        Map<String, Object> userMap = gameLobbyRedis.getAllUsers(roomId);
        UserScoreDto result = new UserScoreDto();
        List<EndScoreDto> endScoreDtos = new ArrayList<>();
        for (Map.Entry<String, Object> entry : userMap.entrySet()) {
            String userIndex = entry.getKey().toString();
            String username = entry.getValue().toString();
            if (username.equals("HOST")) {
                continue;
            }
            Object scoreObj = scoreMap.get(userIndex);
            int score = scoreObj != null ? Integer.parseInt(scoreObj.toString()) : 0;
            endScoreDtos.add(new EndScoreDto(username, score));
        }

        // 점수 기준 내림차순 정렬
        endScoreDtos.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        result.setScores(endScoreDtos);
        result.setType(TypeEnum.SCORE);
        gameLobbyRedis.gameDelete(roomId);
        messagingTemplate.convertAndSend("/topic/game/" + roomId, result);
    }
}
