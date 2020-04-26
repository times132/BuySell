package com.buysell.domain.DTO;

import com.buysell.domain.entity.Role;
import com.buysell.domain.entity.User;
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
