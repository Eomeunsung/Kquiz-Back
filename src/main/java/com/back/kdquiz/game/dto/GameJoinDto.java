package com.back.kdquiz.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameJoinDto {
    private String gameId;
    private String name;
}
