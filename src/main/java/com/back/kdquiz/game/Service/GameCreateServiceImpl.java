package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.Repository.GameLobbyRedis;
import com.back.kdquiz.game.dto.GameCreateDto;
import com.back.kdquiz.quiz.dto.get.QuizGetDto;
import com.back.kdquiz.quiz.service.quizService.get.QuizGetService;
import com.back.kdquiz.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class GameCreateServiceImpl implements GameCreateService {

    private final QuizGetService quizGetService;
    private final GameLobbyRedis gameLobbyRedis;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    @Override
    public ResponseEntity gameCreateResponse(Long quizId) {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for(int i=0; i<CODE_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        GameCreateDto gameCreateDto = new GameCreateDto();
        QuizGetDto quizGetDto = quizGetService.quizGetDto(quizId);
        gameCreateDto.setQuizGetDto(quizGetDto);
        gameCreateDto.setGameId(sb.toString());
        gameLobbyRedis.gameCreate(sb.toString(), String.valueOf(quizId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("G200", "게임 생성 성공", gameCreateDto));
    }
}
