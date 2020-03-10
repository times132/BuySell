package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {


    private Long roomId;
    private String roomName;
    private String request;  //대화요청하는 사람
    private String receiver; //대화요청 받는 사람

    private Integer rqMsgCount;
    private Integer rcMsgCount;

    private LocalDateTime createdDate;

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .receiver(receiver)
                .request(request)
                .rcMsgCount(rcMsgCount)
                .rqMsgCount(rqMsgCount)
                .build();
    }


    @Builder
    public ChatRoomDTO (Long roomId, String roomName, String request, String receiver, Integer rqMsgCount, Integer rcMsgCount, LocalDateTime createdDate) {
        this.roomId =roomId;
        this.roomName= roomName;
        this.request = request;
        this.receiver = receiver;
        this.rcMsgCount = rcMsgCount;
        this.rqMsgCount= rqMsgCount;
        this.createdDate = createdDate;
    }
}
