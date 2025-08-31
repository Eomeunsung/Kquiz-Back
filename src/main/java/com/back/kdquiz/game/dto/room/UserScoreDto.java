package com.back.kdquiz.game.dto.room;

import com.back.kdquiz.game.dto.room.EndScoreDto;
import com.back.kdquiz.game.enums.TypeEnum;
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
