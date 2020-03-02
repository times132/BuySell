package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatRoom;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {


    private Long roomId;
    private String roomName;



    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .build();
    }

    @Builder
    public ChatRoomDTO(Long roomId, String roomName) {
        this.roomId =roomId;
        this.roomName= roomName;

    }
}
