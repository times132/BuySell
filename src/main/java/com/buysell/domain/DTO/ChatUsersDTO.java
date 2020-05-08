package com.buysell.domain.DTO;

import com.buysell.domain.entity.ChatRoom;
import com.buysell.domain.entity.User;
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

    @Builder
    public ChatUsersDTO(Long cid, User user, ChatRoom chatRoom, Integer msgCount){
        this.cid = cid;
        this.msgCount = msgCount;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
