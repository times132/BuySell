package com.example.giveandtake.model.entity;


import com.example.giveandtake.model.audit.DateAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatrooms")
public class ChatRoom{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

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
    public ChatRoom(Long roomId , String roomName, String request, String receiver,Integer rqMsgCount,Integer rcMsgCount, LocalDateTime msgDate)
    {
        this.roomId = roomId;
        this.roomName =roomName;
        this.request = request;
        this.receiver = receiver;
        this.rqMsgCount =rqMsgCount;
        this.rcMsgCount=rcMsgCount;
        this.msgDate = msgDate;
    }


}


