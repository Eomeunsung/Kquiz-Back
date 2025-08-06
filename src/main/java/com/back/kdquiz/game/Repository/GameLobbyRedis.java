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
    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private HashOperations<String, String, Object> hashOps;

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
    }

    public void gameCreate(String gameId, String quizId){
        valueOperations.set("game:quiz:"+gameId, quizId);
    }

    public void gameDelete(String gameId){
        // 삭제할 키 목록
        String quizKey = "game:quiz:" + gameId;
        String usersKey = "game:users:" + gameId;
        String usersIndexKey = "game:users:index:" + gameId;
        String scoresKey = "game:scores:" + gameId;

        // 실제 삭제 수행
        redisTemplate.delete(quizKey);
        redisTemplate.delete(usersKey);
        redisTemplate.delete(usersIndexKey);
        redisTemplate.delete(scoresKey);
    }

    public Long addUser(String gameId, String username){
        Long newIndex = redisTemplate.opsForValue().increment("game:users:index:"+gameId); //기존에 유저 값이 있으면 +1 해서 새로운 유저 id 생성

        hashOps.put("game:users:"+gameId, String.valueOf(newIndex), username);
        String key = "game:users:" + gameId;
        System.out.println("✅ Added: key = " + key + ", index = " + newIndex + ", username = " + username);
        return newIndex;
    }


    //유저 id 가져오기
    public String getUser(String gameId, String index){
        return (String) hashOps.get("game:users:"+gameId, index);
    }

    //모든 유저 id 가져오기
    public Map<String, Object> getAllUsers(String gameId){
        String key = "game:users:" + gameId;
        Map<String, Object> users = hashOps.entries(key);
        System.out.println("🔍 All users for key = " + key + " => " + users);
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
    public Map<String, Object> getAllScores(String gameId) {
        return hashOps.entries("game:scores:" + gameId);
    }


}
