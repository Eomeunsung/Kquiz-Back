package com.back.kdquiz.game.dto.room;

import com.back.kdquiz.game.enums.TypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimerResDto {
    private TypeEnum type;
    private int timer;
}
