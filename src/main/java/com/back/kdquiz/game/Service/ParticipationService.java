package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ParticipationService {

    private final GameLobbyRedis gameLobbyRedis;

    @Transactional
    public ResponseDto<?> participation(String roomId){
        try{
            Integer id = gameLobbyRedis.get(roomId);
            log.info("게임 아이디 "+id);
            if(id==null){
                return ResponseDto.setFailed("P000", "게임을 못 찾음");
            }
            return ResponseDto.setSuccess("P200", "게임 접속 성공");
        }catch (Exception e){
            return ResponseDto.setFailed("P001", "게임을 못 찾음"+e.getMessage());
        }

    }
}
