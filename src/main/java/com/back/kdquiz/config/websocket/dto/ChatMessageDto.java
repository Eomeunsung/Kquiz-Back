package com.back.kdquiz.config.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String roomId;
    private String userId;
    private String name;
    private String content;
}
