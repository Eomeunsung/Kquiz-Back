package com.back.kdquiz.config.websocket.chat.dto;

import com.back.kdquiz.config.websocket.chat.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ChatMessageDto {
    private String roomId;
    private Long userId;
    private String name;
    private String content;
    private TypeEnum type;
    private Map<Object, Object> userList;
}
