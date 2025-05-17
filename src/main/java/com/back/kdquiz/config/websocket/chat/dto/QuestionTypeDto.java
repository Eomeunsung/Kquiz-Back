package com.back.kdquiz.config.websocket.chat.dto;

import com.back.kdquiz.config.websocket.chat.enums.TypeEnum;
import com.back.kdquiz.quiz.dto.get.QuestionGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionTypeDto {
    private QuestionGetDto question;
    private TypeEnum type;
    private LocalDateTime localDateTime;
}
