package com.example.giveandtake.model.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatrooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column
    private String roomName;

    @Column
    private String request;

    @Column
    private String receiver;



    @Builder
    public ChatRoom(Long roomId , String roomName, String request, String receiver) {
        this.roomId = roomId;
        this.roomName =roomName;
        this.request = request;
        this.receiver = receiver;
    }


}


