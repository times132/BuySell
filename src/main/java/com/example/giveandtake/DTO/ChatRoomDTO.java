package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.ChatUsers;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {


    private String roomId;
    private String roomName;
    private List<ChatUsers> users = new ArrayList<>();

    private LocalDateTime msgDate;

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .users(users)
                .msgDate(msgDate)
                .build();
    }


    @Builder
    public ChatRoomDTO (String roomId, String roomName,List<ChatUsers> users, LocalDateTime msgDate) {
        this.roomId =roomId;
        this.roomName= roomName;
        this.users = users;
        this.msgDate = msgDate;
    }
}
