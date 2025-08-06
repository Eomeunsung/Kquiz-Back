package com.back.kdquiz.game.Service;

import com.back.kdquiz.game.dto.GameJoinDto;
import org.springframework.http.ResponseEntity;

public interface GameJoinService {

    ResponseEntity gameJoinResponse(GameJoinDto gameJoinDto);
}
