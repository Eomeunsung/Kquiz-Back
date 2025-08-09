package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.dto.GameQuizInfoDto;
import org.springframework.http.ResponseEntity;

public interface GameCreateService {

    ResponseEntity gameCreateResponse(Long quizId);

    GameQuizInfoDto gameQuizInfo(Long quizId, String roomId);



}
