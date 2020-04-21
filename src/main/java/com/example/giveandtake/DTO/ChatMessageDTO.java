package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.ChatMessage;

import com.example.giveandtake.model.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatMessageDTO {

    private String roomId;
    private String sender; // 메시지 보낸사람
    private String senderId; // 메시지 보낸사람
    private ChatMessage.MessageType type; // 메시지 타입
    private String message; // 메시지

    private ChatRoom chatRoom;
    private LocalDateTime createdDate;

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .type(type)
                .sender(sender)
                .senderId(senderId)
                .message(message)
                .build();
    }

    @Builder
    public ChatMessageDTO(ChatRoom chatRoom, String sender, String senderId, ChatMessage.MessageType type, String message) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.senderId =senderId;
        this.type = type;
        this.message = message;
    }

}
