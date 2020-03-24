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
    private String roomName;

    @Column
    private LocalDateTime msgDate;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"chatRoom"})
    private List<ChatUsers> users = new ArrayList<>();


    @Builder
    public ChatRoom(String roomId , String roomName, LocalDateTime msgDate, List<ChatUsers> users)
    {
        this.roomId = roomId;
        this.roomName =roomName;
        this.msgDate = msgDate;
        this.users = users;
    }


    @Override
    public int compareTo(ChatRoom chatRoom) {
        return this.msgDate.compareTo(chatRoom.msgDate);
    }
}


