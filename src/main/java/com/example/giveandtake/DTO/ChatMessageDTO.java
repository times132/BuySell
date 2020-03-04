package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatMessage;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatMessageDTO {

    private Long roomId;

    private String sender; // 메시지 보낸사람

    private ChatMessage.MessageType type; // 메시지 타입
    private String message; // 메시지


    private LocalDateTime createdDate;

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .roomId(roomId)
                .type(type)
                .sender(sender)
                .message(message)
                .build();
    }

    @Builder
    public ChatMessageDTO(Long roomId, String sender, ChatMessage.MessageType type, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.type = type;
        this.message = message;

    }
}
