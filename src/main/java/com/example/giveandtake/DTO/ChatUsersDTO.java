package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.ChatUsers;
import com.example.giveandtake.model.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatUsersDTO{

    private Long cid;

    private User user;
    private ChatRoom chatRoom;


    private Integer msgCount;


//    public ChatUsers toEntity() {
//        return ChatUsers.builder()
//                .cid(cid)
//                .user(user)
//                .chatRoom(chatRoom)
//                .msgCount(msgCount)
//                .build();
//    }
//
//
//    @Builder
//    public ChatUsersDTO(Long cid, User user, ChatRoom chatRoom, Integer msgCount){
//        this.cid = cid;
//        this.msgCount = msgCount;
//        this.user = user;
//        this.chatRoom = chatRoom;
//    }
}
