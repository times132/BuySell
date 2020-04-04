package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.DTO.ChatUsersDTO;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.ChatUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatUsers userToEntity(ChatUsersDTO dto);
    ChatUsersDTO toDTO(ChatUsers chatUsers);
    ChatRoomDTO RoomToDto(ChatRoom chatRoom);
}
