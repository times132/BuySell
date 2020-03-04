package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import lombok.*;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {


    private Long roomId;
    private String roomName;
    private String request;  //대화요청하는 사람
    private String receiver; //대화요청 받는 사람
    private List<User> chatMembers = new ArrayList<>();


    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .receiver(receiver)
                .request(request)
                .build();
    }

    @Builder
    public ChatRoomDTO(Long roomId, String roomName, String request, String receiver) {
        this.roomId =roomId;
        this.roomName= roomName;
        this.request = request;
        this.receiver = receiver;
    }
}
