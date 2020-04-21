package com.example.giveandtake.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "chatrooms")
public class ChatRoom implements Comparable<ChatRoom>{

    @Id
    private String roomId;

    @Column
    private String recentMsg;
    @PrePersist
    protected void prePersist(){
        if (this.recentMsg == null) this.recentMsg = "대화내용이 없습니다.";

    }
    @Column
    private LocalDateTime msgDate;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"chatRoom"})
    private List<ChatUsers> users = new ArrayList<>();


    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"chatRoom"})
    private List<ChatMessage> messages = new ArrayList<>();

    @Builder
    public ChatRoom(String roomId , String recentMsg, LocalDateTime msgDate, List<ChatUsers> users, List<ChatMessage> messages)
    {
        this.roomId = roomId;
        this.recentMsg =recentMsg;
        this.msgDate = msgDate;
        this.messages = messages;
        this.users = users;
    }


    @Override
    public int compareTo(ChatRoom chatRoom) {
        return this.msgDate.compareTo(chatRoom.msgDate);
    }
}


