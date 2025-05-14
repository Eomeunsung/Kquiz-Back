package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.game.dto.GameCreateDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import com.back.kdquiz.quiz.service.quizSerivce.QuizGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class GameCreateService {

    private final QuizGetService quizGetService;
    private final GameLobbyRedis gameLobbyRedis;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public ResponseDto<GameCreateDto> gameCreate(Long quizId){
        try{
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for(int i=0; i<CODE_LENGTH; i++){
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            GameCreateDto gameCreateDto = new GameCreateDto();
            ResponseDto responseDto = quizGetService.quizGet(quizId);
            if(responseDto.getData()!=null){
                gameCreateDto.setQuizGetDto((QuizGetDto) responseDto.getData());
                gameCreateDto.setGameId(sb.toString());
                String strId = String.valueOf(quizId);
                gameLobbyRedis.gameCreate(sb.toString(), strId);
            }else{
                return ResponseDto.setFailed("G000", responseDto.getMessage());
            }
            return ResponseDto.setSuccess("G200", "게임 생성 성공", gameCreateDto);
        }catch (Exception e){
            return ResponseDto.setFailed("G000", "게임 생성 오류"+e.getMessage());
        }
    }
}
