package com.back.kdquiz.game.Service;

import org.springframework.http.ResponseEntity;

public interface ParticipationService {

    ResponseEntity participationResponse(String roomId);
}
