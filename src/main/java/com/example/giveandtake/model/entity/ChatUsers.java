package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @JsonIgnoreProperties({"replyList", "boardList", "password", "email", "phone", "roles", "provider", "username", "name", "createdDate", "updatedDate", "activation","chats"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="roomId")
    @JsonIdentityReference(alwaysAsId=true)
    private ChatRoom chatRoom;


    @Column
    private Integer msgCount;

    @PrePersist
    protected void prePersist(){
        if (this.msgCount == null) this.msgCount = 0;

    }

    @Builder
    public ChatUsers(Long cid, User user, ChatRoom chatRoom, Integer msgCount){
        this.cid = cid;
        this.msgCount = msgCount;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
