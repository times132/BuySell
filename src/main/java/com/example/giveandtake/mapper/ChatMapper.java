package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.ChatUsersDTO;
import com.example.giveandtake.model.entity.ChatUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatUsers userToEntity(ChatUsersDTO dto);
}
