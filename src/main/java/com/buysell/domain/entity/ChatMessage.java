package com.buysell.domain.entity;

import com.buysell.domain.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        TALK, QUIT, BOARD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgNum;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties({"recentMsg", "msgCnt", "messages"})
    private ChatRoom chatRoom; // 방번호

    @Column
    private String sender; // 메시지 보낸사람

    @Column
    private String senderId; // 메시지 보낸사람

    @Enumerated(EnumType.STRING)
    private MessageType type; // 메시지 타입

    @Column
    private String message; // 메시지 내용


    @Builder
    public ChatMessage(ChatRoom chatRoom , String sender, String senderId, MessageType type, String message) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.senderId = senderId;
        this.type = type;
        this.message = message;

    }

}
