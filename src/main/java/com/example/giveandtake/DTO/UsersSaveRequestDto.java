package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {

    private String nickname;
    private String username;
    private String password;
    private String email;
    private String phone;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .username(username)
                .email(email)
                .password(password)
                .phone(phone)
                .build();
    }
}
