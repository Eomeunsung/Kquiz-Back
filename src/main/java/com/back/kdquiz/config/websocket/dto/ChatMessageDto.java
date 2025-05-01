package com.back.kdquiz.config.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String name;
    private String content;
}
