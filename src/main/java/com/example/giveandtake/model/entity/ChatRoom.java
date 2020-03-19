package com.example.giveandtake.model.entity;


import com.example.giveandtake.model.audit.DateAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatrooms")
public class ChatRoom implements Comparable<ChatRoom>{

    @Id
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String request;

    @Column
    private String receiver;

    @Column
    private Integer rqMsgCount;

    @Column
    private Integer rcMsgCount;


    @Column
    private LocalDateTime msgDate;



    @Builder
    public ChatRoom(String roomId , String roomName, String request, String receiver,Integer rqMsgCount,Integer rcMsgCount, LocalDateTime msgDate)
    {
        this.roomId = roomId;
        this.roomName =roomName;
        this.request = request;
        this.receiver = receiver;
        this.rqMsgCount =rqMsgCount;
        this.rcMsgCount=rcMsgCount;
        this.msgDate = msgDate;
    }


    @Override
    public int compareTo(ChatRoom chatRoom) {
        return this.msgDate.compareTo(chatRoom.msgDate);
    }
}


