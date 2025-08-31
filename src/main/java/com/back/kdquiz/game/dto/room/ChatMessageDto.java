package com.back.kdquiz.game.dto.room;

import com.back.kdquiz.game.enums.TypeEnum;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private Long userId;
    private String name;
    private String content;
    private TypeEnum type;
    private Map<String, Object> userList;

}
