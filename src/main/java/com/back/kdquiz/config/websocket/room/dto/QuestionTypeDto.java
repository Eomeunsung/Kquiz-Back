package com.back.kdquiz.config.websocket.room.dto;

import com.back.kdquiz.config.websocket.room.enums.TypeEnum;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionTypeDto {
    private QuestionGetDto question;
    private TypeEnum type;
}
