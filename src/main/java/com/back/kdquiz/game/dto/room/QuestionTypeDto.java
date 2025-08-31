package com.back.kdquiz.game.dto.room;

import com.back.kdquiz.game.enums.TypeEnum;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionTypeDto {
    private QuestionGetDto question;
    private TypeEnum type;
}
