package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatusers")
public class ChatUsers{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"replyList", "boardList", "password", "email", "phone", "roles", "provider", "username", "name", "createdDate", "updatedDate", "activation"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties({"roomName"})
    @JsonIdentityReference(alwaysAsId=true)
    private ChatRoom chatRoom;


    @Column
    private Integer msgCount;



    @Builder
    public ChatUsers(Long cid, User user, ChatRoom chatRoom, Integer msgCount){
        this.cid = cid;
        this.msgCount = msgCount;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
