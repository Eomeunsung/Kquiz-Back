package com.back.kdquiz.game.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GameLobbyRedis {
    private final RedisTemplate<Object, Object> redisTemplate;
    private ValueOperations<Object, Object> valueOperations;
    private HashOperations<Object, Object, Object> hashOps;

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
    }

    public void gameCreate(String gameId, String quizId){
        valueOperations.set("game:quiz:"+gameId, quizId);
    }

    public Long addUser(String gameId, String username){
        Long newIndex = redisTemplate.opsForValue().increment("game:users:index:"+gameId); //기존에 유저 값이 있으면 +1 해서 새로운 유저 id 생성

        hashOps.put("game:users:"+gameId, String.valueOf(newIndex), username);
        return newIndex;
    }


    //유저 id 가져오기
    public String getUser(String gameId, String index){
        return (String) hashOps.get("game:users:"+gameId, index);
    }

    //모든 유저 id 가져오기
    public Map<Object, Object> getAllUsers(String gameId){
        return hashOps.entries("game:users:"+gameId);
    }

    //퀴즈 id 가져오기
    public String getQuiz(String gameId){
        return (String) valueOperations.get("game:quiz:"+gameId);
    }

    //유저 id 삭제
    public void removeUser(String gameId, String userId){
        hashOps.delete("game:users:"+gameId, userId);
    }

    // 점수 추가
    public void addScore(String gameId, String userId, int scoreToAdd) {
        // 기존 점수 불러오기
        Object scoreObj = hashOps.get("game:scores:" + gameId, userId);
        int currentScore = scoreObj != null ? Integer.parseInt(scoreObj.toString()) : 0;
        int newScore = currentScore + scoreToAdd;
        // 갱신
        hashOps.put("game:scores:" + gameId, userId, String.valueOf(newScore));
    }

    // 개별 유저 점수 조회
    public int getScore(String gameId, String userId) {
        Object scoreObj = hashOps.get("game:scores:" + gameId, userId);
        return scoreObj != null ? Integer.parseInt(scoreObj.toString()) : 0;
    }

    // 전체 유저 점수 조회
    public Map<Object, Object> getAllScores(String gameId) {
        return hashOps.entries("game:scores:" + gameId);
    }
}
