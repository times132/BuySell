package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.DTO.UserRolesDTO;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRoles userRolestoEntity(UserRolesDTO dto);

    UserDTO convertEntityToDto(User user);
    User toEntity(UserDTO userDTO);
}