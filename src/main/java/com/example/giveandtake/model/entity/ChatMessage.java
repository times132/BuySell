package com.example.giveandtake.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "chatmessages")
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    @Id
    @GeneratedValue
    private Long msgNum;

    @Column
    private Long roomId; // 방번호

    @Column
    private String sender; // 메시지 보낸사람

    @Enumerated(EnumType.STRING)
    private MessageType type; // 메시지 타입

    @Column
    private String message; // 메시지


    @Column
    private  String receiver; //메시지 받는 사람


    @Builder
    public ChatMessage(Long roomId , String sender, MessageType type, String message, String receiver) {
        this.roomId = roomId;
        this.sender = sender;
        this.type = type;
        this.message = message;
        this.receiver= receiver;
    }

}
