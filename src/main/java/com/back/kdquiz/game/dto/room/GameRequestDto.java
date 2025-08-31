package com.back.kdquiz.game.dto.room;

import com.back.kdquiz.game.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRequestDto {
    private String content;
    private TypeEnum type;
}
