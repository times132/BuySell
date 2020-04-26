package com.buysell.mapper;

import com.buysell.domain.DTO.UserDTO;
import com.buysell.domain.DTO.UserRolesDTO;
import com.buysell.domain.entity.User;
import com.buysell.domain.entity.UserRoles;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRoles userRolestoEntity(UserRolesDTO dto);

    UserDTO convertEntityToDto(User user);
    User toEntity(UserDTO userDTO);
}