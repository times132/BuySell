package com.buysell.mapper;

import com.buysell.domain.DTO.ChatRoomDTO;
import com.buysell.domain.DTO.ChatUsersDTO;
import com.buysell.domain.entity.ChatRoom;
import com.buysell.domain.entity.ChatUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatUsers userToEntity(ChatUsersDTO dto);
    ChatUsersDTO toDTO(ChatUsers chatUsers);
    ChatRoomDTO RoomToDto(ChatRoom chatRoom);
}
