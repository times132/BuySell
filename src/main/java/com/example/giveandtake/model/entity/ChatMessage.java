package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatmessages")
public class ChatMessage extends DateAudit {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgNum;

    @Column
    private Long roomId; // 방번호

    @Column
    private String sender; // 메시지 보낸사람

    @Enumerated(EnumType.STRING)
    private MessageType type; // 메시지 타입

    @Column
    private String message; // 메시지


    @Builder
    public ChatMessage(Long roomId , String sender, MessageType type, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.type = type;
        this.message = message;

    }

}
