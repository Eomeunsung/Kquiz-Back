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
        Long newIndex = redisTemplate.opsForValue().increment("game:users:index:"+gameId);

        hashOps.put("game:users:"+gameId, String.valueOf(newIndex), username);
        return newIndex;
    }

    public String getUser(String gameId, String index){
        return (String) hashOps.get("game:users:"+gameId, index);
    }

    public Map<Object, Object> getAllUsers(String gameId){
        return hashOps.entries("game:users:"+gameId);
    }


    public String getQuiz(String gameId){
        return (String) valueOperations.get("game:quiz:"+gameId);
    }

    public void removeUser(String gameId, String userId){
        hashOps.delete("game:users:"+gameId, userId);
    }

}
