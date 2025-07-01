package com.back.kdquiz.game.Service;

import org.springframework.http.ResponseEntity;

public interface GameCreateService {

    ResponseEntity gameCreateResponse(Long quizId);

}
