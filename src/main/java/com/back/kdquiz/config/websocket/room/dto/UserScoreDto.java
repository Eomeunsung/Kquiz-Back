package com.back.kdquiz.config.websocket.room.dto;

import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserScoreDto {
    private List<EndScoreDto> scores;
    private TypeEnum type;
}
