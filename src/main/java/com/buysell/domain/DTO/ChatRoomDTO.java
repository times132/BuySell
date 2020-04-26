package com.buysell.domain.DTO;

import com.buysell.domain.entity.ChatMessage;
import com.buysell.domain.entity.ChatRoom;
import com.buysell.domain.entity.ChatUsers;
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
    private String recentMsg;
    private List<ChatUsers> users = new ArrayList<>();
    private List<ChatMessage> messages = new ArrayList<>();

    private LocalDateTime msgDate;


    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomId(roomId)
                .recentMsg(recentMsg)
                .messages(messages)
                .users(users)
                .msgDate(msgDate)
                .build();
    }


    @Builder
    public ChatRoomDTO (String roomId, String recentMsg,List<ChatUsers> users, List<ChatMessage> messages, LocalDateTime msgDate) {
        this.roomId =roomId;
        this.recentMsg= recentMsg;
        this.users = users;
        this.messages = messages;
        this.msgDate = msgDate;
    }
}
