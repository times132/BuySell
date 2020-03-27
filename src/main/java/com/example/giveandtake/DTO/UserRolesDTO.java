package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRolesDTO {

    private Long id;
    private User user;
    private Role role;

}
