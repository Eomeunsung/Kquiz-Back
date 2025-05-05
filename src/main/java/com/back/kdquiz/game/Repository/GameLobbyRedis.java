package com.back.kdquiz.game.Repository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GameLobbyRedis {
    private final RedisTemplate<Object, Object> redisTemplate;
    private ValueOperations<Object, Object> valueOperations;

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
    }

    public void gameCreate(String gameId, Long quizId){
        valueOperations.set(gameId, quizId);
    }

    public Integer get(String gameId){
        return (Integer) valueOperations.get(gameId);
    }

}
